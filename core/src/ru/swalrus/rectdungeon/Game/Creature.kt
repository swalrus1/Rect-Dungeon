package ru.swalrus.rectdungeon.Game

import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Effects.Buff
import ru.swalrus.rectdungeon.Items.Weapon
import ru.swalrus.rectdungeon.Utils
import kotlin.math.exp

abstract class Creature (var x: Int, var y: Int, var HP: Int, var img: Texture, var room: Room) {

    open var ready : Boolean = true             // Is ready to make next turn
    var buffs: Array<Buff> = emptyArray()

    private var active : Boolean = true         // Is not sleeping
    private var sprite: Sprite = Sprite(img)
    private var action: Char = 'n'              // {Nothing, Move, Attack}
    private var moveDir: Vector2 = Vector2()
    private var dTime: Float = 0f
    private var animTime: Float = 0f
    private var target: Creature? = null
    private var afterAttack: (attacker: Creature, defender: Creature) -> Unit = {_, _ -> }
    private var dPos: Vector2 = Vector2()
    private var rotated: Boolean = false        // If creature has been rotated during the current movement
    private var lookRight: Boolean = true

// TODO: kill(), dealDamage(), abstract onDeath()
    init {
        // Add creature to the queue
        room.addCreature(this)

        // Set position of sprite
        align()
        sprite.setSize(Const.TILE_SIZE, Const.TILE_SIZE)
        sprite.setOriginCenter()
    }

    abstract fun act()


    private fun makeTurn() {
        for (buff in buffs) {
            buff.onTurn(this)
        }

        act()
    }

    fun render(batch: SpriteBatch) {
        update()
        sprite.draw(batch)
    }

    fun isActive() : Boolean {
        return active
    }

    open fun move(direction : Int, force: Boolean = false) {
        moveDir = Vector2(Utils.dir2vec(direction))
        val newX = x + moveDir.x.toInt()
        val nexY = y + moveDir.y.toInt()
        val tile = room.getTile(newX, nexY)

        if (tile.passable or ((this is Player) and (tile is Door))) {
            val creature = room.getCreatureAt(newX, nexY)
            // If there are no creature
            if (creature == null) {
                startAnim()
                dTime = 0f
                animTime = Const.MOVE_TIME
                action = 'm'
            } else {
                moveDir = Vector2.Zero
                endMove()
            }
        } else {
            moveDir = Vector2.Zero
            endMove()
        }
    }

    open fun attack(direction: Int, target: Creature,
                    afterAttack: (attacker: Creature, defender: Creature) -> Unit) {
        moveDir = Vector2(Utils.dir2vec(direction))
        startAnim()
        animTime = Const.ATTACK_TIME
        action = 'a'
        this.target = target
        this.afterAttack = afterAttack
    }

    fun inAnim() : Boolean {
        return (!moveDir.isZero)
    }


    private fun update() {
        // Move creature
        if (inAnim()) {
            // Check if creature has reached the destination
            if (dTime >= animTime) {
                when (action) {
                    'm' -> endMove()
                    'a' -> endAttack()
                }
            } else {
                align()
                dPos = Vector2(moveDir)
                dPos.scl(Const.TILE_SIZE * when (action) {
                    'm' -> moveFun(dTime / animTime)
                    'a' -> attackFun(dTime / animTime)
                    else -> 0f
                })
                sprite.translate(dPos.x, dPos.y)
                dTime += graphics.deltaTime
                // Change look direction if necessary
                if (!rotated and (dTime >= Const.ROTATE_TIME * animTime)) {
                    changeSpriteDirection(Utils.vec2dir(moveDir))
                }
            }
        }
    }

    // Set sprite position to the center of the tile
    private fun align() {
        val xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
        val yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
        sprite.setPosition(xPos, yPos)
    }

    // Move function (y ~ (0; 1))
    private fun moveFun(x: Float) : Float {
        return 1 / (1 + exp(5 - 10 * x))
    }

    private fun attackFun(x: Float) : Float {
        return if (x > 0.5f) {
            (x-1) * (x-1)
        } else {
            x * x
        } // TODO: Сделать параболу более выгнутой (резкий выпад)
    }

    private fun startAnim() {
        ready = false
        dTime = 0f
        rotated = false
    }

    open fun endMove() {
        x += moveDir.x.toInt()
        y += moveDir.y.toInt()
        moveDir = Vector2.Zero
        align()
        ready = true
        room.getTile(x, y).onStand(this)
        action = 'n'
    }

    open fun endAttack() {
        moveDir = Vector2.Zero
        align()
        ready = true
        action = 'n'
        afterAttack(this, target!!)
    }

    private fun changeSpriteDirection(dir: Int) {
        if (lookRight and (dir == Const.LEFT)) {
            sprite.setScale(-1f, 1f)
            lookRight = false
        } else if (!lookRight and (dir == Const.RIGHT)) {
            sprite.setScale(1f, 1f)
            lookRight = true
        }
    }
}