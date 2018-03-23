package ru.swalrus.rectdungeon.Game

import com.badlogic.gdx.Gdx.app
import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Effects.Buff
import ru.swalrus.rectdungeon.Items.Item
import ru.swalrus.rectdungeon.Utils
import java.util.*
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.roundToInt

abstract class Creature (var x: Int, var y: Int, var HP: Int, var img: Texture, var room: Room) {

    open var ready : Boolean = true             // Is ready to make next turn
    var buffs: MutableList<Buff> = MutableList(0, { _ -> null!! })
    var alive: Boolean = true
    private var active : Boolean = true         // Is not sleeping
    private var sprite: Sprite = Sprite(img)
    var actionQueue: ArrayDeque<() -> Unit> = ArrayDeque()

    private var action: Char = 'n'              // { Nothing, Move, Attack, Push, Die }
    private var moveDir: Vector2 = Vector2()
    private var dTime: Float = 0f
    private var animTime: Float = 0f
    private var target: Creature? = null
    private var afterAttack: (attacker: Creature, defender: Creature) -> Unit = {_, _ -> }
    private var dPos: Vector2 = Vector2()
    private var rotated: Boolean = false        // If creature has been rotated during the current movement
    private var attacked: Boolean = false       // If creature has attacked during the current attack
    private var lookRight: Boolean = true

    private var indicatorState: Char = 'n'      // { Nothing, Image, Text }
    private var indicatorDTime: Float = 0f
    private var indicatorY: Float = 0f
    private var indicatorText: String = ""
    private var indicatorImg: Texture = Utils.getImg("loot_icon")
    private var font: BitmapFont = Const.indicatorFont

    var throwItem: Item? = null
    private var throwTime: Float = 0f
    private var throwDir: Vector2 = Vector2.Zero
    private var throwPos: Float = 0f            // Passed distance / all distance
    private var throwDTime: Float = 0f


    init {
        // Add creature to the queue
        room.addCreature(this)

        // Set position of sprite
        align()
        sprite.setSize(Const.TILE_SIZE, Const.TILE_SIZE)
        sprite.setOriginCenter()

        room.getTile(x, y).onStand(this)
    }

    abstract fun act()

    open fun onDeath() {

    }


    fun startTurn() {
        for (buff in buffs) {
            buff.onTurn(this)
        }

        ready = false
        act()
    }

    open fun endTurn() {
        ready = true
    }

    fun nextAction() {
        if (isMakingTurn() && !inAnim() && !actionQueue.isEmpty()) {
            actionQueue.pop()()
        } else {
            app.log("Turn system", "request for next action is not correct (nextAction)")
        }
    }

    fun render(batch: SpriteBatch) {
        update()
        if (alive) {
            sprite.draw(batch)
        }
        when (indicatorState) {
            't' -> font.draw(batch, indicatorText,
                    (x + 1 + Const.INDICATOR_OFFSET_X) * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT,
                    (y + Const.INDICATOR_OFFSET_Y) * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM + indicatorY)
            'i' -> batch.draw(indicatorImg,
                    (x + 1 + Const.INDICATOR_OFFSET_X) * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT,
                    (y + Const.INDICATOR_OFFSET_Y) * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM + indicatorY,
                    Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
        }
        if (!throwDir.isZero) {
            batch.draw(throwItem!!.img,
                    throwDir.x * throwPos + sprite.x + Const.TILE_SIZE * (1 - Const.THROW_SCALE) / 2,
                    throwDir.y * throwPos + sprite.y + Const.TILE_SIZE * (1 - Const.THROW_SCALE) / 2,
                    Const.TILE_SIZE * Const.THROW_SCALE, Const.TILE_SIZE * Const.THROW_SCALE,
                    0f, 1f, 1f, 0f)
        }
    }

    fun isActive() : Boolean {
        return active && alive
    }

    // Returns true if the creature will move
    open fun move(direction : Char, force: Boolean = false) : Boolean {
        moveDir = Vector2(Utils.dir2vec(direction))
        val newX = x + moveDir.x.toInt()
        val nexY = y + moveDir.y.toInt()
        val tile = room.getTile(newX, nexY)

        if (force or tile.passable or ((this is Player) and (tile is Door))) {
            val creature = room.getCreatureAt(newX, nexY)
            // If there are no creature
            if (force or (creature == null)) {
                startAnim()
                dTime = 0f
                animTime = Const.MOVE_TIME
                action = 'm'
                return true
            } else {
                moveDir = Vector2.Zero
                endAnim()
                return false
            }
        } else {
            moveDir = Vector2.Zero
            endAnim()
            return false
        }
    }

    open fun attack(direction: Char, target: Creature,
                    afterAttack: (attacker: Creature, defender: Creature) -> Unit,
                    requiredAP: Int, resetAP: Boolean) {
        // TODO: Make animation (change sprite in attack)
        if (direction != Const.CENTER) {
            moveDir = Vector2(Utils.dir2vec(direction))
            startAnim()
            animTime = Const.ATTACK_TIME
            action = 'a'
            this.target = target
            this.afterAttack = afterAttack
        } else {
            afterAttack(this, target)
            endAttack()
        }
    }

    open fun throwItem(item: Item, x: Int, y: Int) {
        throwItem = item
        throwDir = Vector2((x - this.x) * Const.TILE_SIZE, (y - this.y) * Const.TILE_SIZE)
        throwTime = throwDir.dst(0f, 0f) / Const.TILE_SIZE * Const.THROW_TIME
        throwPos = 0f
        throwDTime = 0f

        push(throwDir)
    }

    open fun dealDamage(damage: Float, direction: Char = Const.CENTER) {
        dealDamage(damage, Utils.dir2vec(direction))
    }

    open fun dealDamage(damage: Float, direction: Vector2) {
        // TODO: Calculate damage
        if (!direction.isZero) {
            push(direction)
        }
        HP -= damage.toInt()
        if (damage < 0) {
            font.color = Const.GREEN
            playIndicator((-(damage.toInt())).toString())
        } else {
            font.color = Const.RED
            playIndicator(damage.toInt().toString())
        }

        // Kill creature if HP <= 0
        if (HP <= 0) {
            die()
        }
    }

    fun addBuff(buff: Buff) {
        buffs.add(buff)
    }

    fun setSpriteImg(texture: Texture) {
        img = texture
        sprite.texture = texture
    }

    fun playIndicator(img: Texture) {
        indicatorState = 'i' // TODO
        indicatorDTime = 0f
        indicatorImg = img
    }

    fun playIndicator(text: String) {
        indicatorState = 't'
        indicatorDTime = 0f
        indicatorText = text
    }

    fun push(direction: Vector2) {
        moveDir = Vector2(direction)
        moveDir.nor()
        startAnim()
        animTime = Const.PUSH_TIME
        action = 'p'
    }

    fun dropLoot(item: Item) {
        playIndicator(Utils.getImg("loot_icon"))
        val player = room.findPlayer()
        if (player != null) {
            player.addItem(item)
        }
    }


    fun inAnim() : Boolean {
        return action != 'n'
    }

    fun isMakingTurn() : Boolean {
        return !ready
    }

    open fun die() {
        onDeath()
        startAnim()
        animTime = Const.INDICATOR_TIME
        action = 'd'
        alive = false
    }


    private fun update() {
        if (isMakingTurn() && !inAnim()) {
            if (!actionQueue.isEmpty()) {
                nextAction()
            } else {
                endTurn()
            }
        }
        // Move creature
        if (inAnim()) {
            // Check if creature has reached the destination
            if (dTime >= animTime) {
                when (action) {
                    'm' -> endMove()
                    'a' -> endAttack()
                    'p' -> endAnim()
                    'd' -> room.removeCreatureAt(x, y)
                }
            } else {
                align()
                dPos = Vector2(moveDir)
                dPos.scl(Const.TILE_SIZE * when (action) {
                    'm' -> moveFun(dTime / animTime)
                    'a' -> attackFun(dTime / animTime)
                    'p' -> pushFun(dTime / animTime)
                    else -> 0f
                })
                sprite.translate(dPos.x, dPos.y)
                dTime += graphics.deltaTime
                // Change look direction if necessary
                if (!rotated and (action != 'p') and (dTime >= Const.ROTATE_TIME * animTime)) {
                    changeSpriteDirection(Utils.vec2dir(moveDir))
                }
                // Attack creature if necessary
                if ((action == 'a') and (!attacked) and (dTime >= 0.5f * Const.ATTACK_TIME)) {
                    afterAttack(this, target!!)
                    attacked = true
                }
            }
        }

        if (indicatorState != 'n') {
            if (indicatorDTime >= Const.INDICATOR_TIME) {
                indicatorDTime = 0f
                indicatorState = 'n'
            } else {
                indicatorY = Const.TILE_SIZE * indicatorFun(indicatorDTime / Const.INDICATOR_TIME)
                indicatorDTime += graphics.deltaTime
            }
        }

        if (!throwDir.isZero) {
            if (throwDTime > throwTime) {
                val target = room.getCreatureAt((x + throwDir.x / Const.TILE_SIZE).roundToInt(),
                        (y + throwDir.y / Const.TILE_SIZE).roundToInt())
                target?.push(throwDir)
                throwItem!!.land(target, this)
                throwDir.setZero()
            } else {
                throwPos = throwFun(throwDTime / throwTime)
                throwDTime += graphics.deltaTime
            }
        }
    }

    // Set sprite position to the center of the tile
    private fun align() {
        val xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
        val yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
        sprite.setPosition(xPos, yPos)
    }

    // Move function (x, y ~ (0; 1))
    private fun moveFun(x: Float) : Float {
        return 1 / (1 + exp(5 - 10 * x))
    }

    // Attack function (x, y ~ (0; 1))
    private fun attackFun(x: Float) : Float {
        return Const.ATTACK_D_S * (abs(4 * x - 2) - 2) * (abs(4 * x - 2) - 2) / 4
    }

    // Push function (x, y ~ (0; 1))
    private fun pushFun(x: Float) : Float {
        return -Const.PUSH_D_S / 2 * (abs(x - 0.5f) - 0.5f)
    }

    // Indicator function (x, y ~ (0; 1))
    private fun indicatorFun(x: Float) : Float {
        return Const.INDICATOR_D_S * x * (-x + 2)
    }

    // Throw function (x, y ~ (0; 1))
    private fun throwFun(x: Float) : Float {
        return x
    }

    private fun startAnim() {
        ready = false
        dTime = 0f
        rotated = false
        attacked = false
    }

    open fun endMove() {
        x += moveDir.x.toInt()
        y += moveDir.y.toInt()
        align()
        endAnim()
        room.getTile(x, y).onStand(this)
    }

    open fun endAttack() {
        endAnim()
    }

    fun endAnim() {
        action = 'n'
        moveDir = Vector2.Zero
        align()
    }

    private fun changeSpriteDirection(dir: Char) {
        if (lookRight and (dir == Const.LEFT)) {
            sprite.setScale(-1f, 1f)
            lookRight = false
        } else if (!lookRight and (dir == Const.RIGHT)) {
            sprite.setScale(1f, 1f)
            lookRight = true
        }
    }
}