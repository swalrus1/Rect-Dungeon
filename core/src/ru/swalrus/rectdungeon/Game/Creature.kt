package ru.swalrus.rectdungeon.Game

import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Utils
import kotlin.math.exp

abstract class Creature (var x: Int, var y: Int, var HP: Int, var img: Texture, var room: Room) {

    private var active : Boolean = true         // Is not sleeping
    open var ready : Boolean = true             // Is ready to make next turn

    private var sprite: Sprite = Sprite(img)
    private var moveDir: Vector2 = Vector2()
    private var dTime: Float = 0f
    private var dPos: Vector2 = Vector2()
    private var rotated: Boolean = false
    private var lookRight: Boolean = true


    init {
        // Add creature to the queue
        room.addCreature(this)

        // Set position of sprite
        align()
        sprite.setSize(Const.TILE_SIZE, Const.TILE_SIZE)
        sprite.setOriginCenter()
    }

    abstract fun makeTurn()


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
                rotated = false
                dTime = 0f
            } else {
                moveDir = Vector2.Zero
                endMove()
            }
        } else {
            moveDir = Vector2.Zero
            endMove()
        }
    }

    fun inAnim() : Boolean {
        return (!moveDir.isZero)
    }


    private fun update() {
        // Move creature
        if (inAnim()) {
            // Check if creature has reached the destination
            if (dTime >= Const.MOVE_TIME) {
                endMove()
            } else {
                align()
                dPos = Vector2(moveDir)
                dPos.scl(Const.TILE_SIZE * moveFun(dTime))
                sprite.translate(dPos.x, dPos.y)
                dTime += graphics.deltaTime
                if (!rotated and (dTime >= Const.ROTATE_TIME * Const.MOVE_TIME)) {
                    changeSpriteDirection(Utils.vec2dir(moveDir))
                }
            }
        }
    }

    // Set sprite position to the center of the tile
    private fun align () {
        val xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT// + Const.TILE_SIZE / 2
        val yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM// + Const.TILE_SIZE / 2
        sprite.setPosition(xPos, yPos)
    }

    // Move function (y ~ (0; 1))
    private fun moveFun (x: Float) : Float {
        return 1 / (1 + exp(5 - 10 * x / Const.MOVE_TIME))
    }

    private fun startAnim() {
        ready = false
        dTime = 0f
    }

    open fun endMove() {
        x += moveDir.x.toInt()
        y += moveDir.y.toInt()
        moveDir = Vector2.Zero
        align()
        ready = true
        room.getTile(x, y).onStand(this)
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