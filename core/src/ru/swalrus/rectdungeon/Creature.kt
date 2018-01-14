package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx.graphics
import kotlin.math.exp

open class Creature (x: Int, y: Int, img: Texture, room: Room) {

    private var active : Boolean = true         // Is not sleeping
    open var ready : Boolean = true             // Is ready to make next turn

    var x: Int = x
    var y: Int = y
    var room: Room = room
    private var sprite: Sprite = Sprite(img)
    private var moveDir: Vector2 = Vector2()
    private var dTime: Float = 0f
    private var dPos: Vector2 = Vector2()


    init {
        // Add creature to the queue
        room.addCreature(this)

        // Set position of sprite
        align()
        sprite.setSize(Const.TILE_SIZE, Const.TILE_SIZE)
    }


    fun render(batch: SpriteBatch) {
        update()
        sprite.draw(batch)
    }


    open fun makeTurn() {

    }

    fun isActive() : Boolean {
        return active
    }

    open fun move(direction : Int, force: Boolean = false) {
        moveDir = Vector2(Const.dir2vec[direction])
        val newX = x + moveDir.x.toInt()
        val nexY = y + moveDir.y.toInt()
        val tile = room.getTile(newX, nexY)

        if (tile.passable or ((this is Player) and (tile is Door))) {
            val creature = room.getCreature(newX, nexY)
            // If there are no creature
            if (creature == null) {
                startAnim()
                dTime = 0f
            } else {
                moveDir = Vector2.Zero
            }
        } else {
            moveDir = Vector2.Zero
        }
    }

    fun inAnim() : Boolean {
        return (!moveDir.isZero)
    }


    private fun update() {
        // Move creature
        if (!moveDir.isZero) {
            // Check if creature has reached the destination
            if (dTime >= Const.MOVE_TIME) {
                endMove()
            } else {
                align()
                dPos = Vector2(moveDir)
                dPos.scl(Const.TILE_SIZE * moveFun(dTime))
                sprite.translate(dPos.x, dPos.y)
                dTime += graphics.deltaTime
            }
        }
    }

    // Set sprite position to the center of the tile
    private fun align () {
        val xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
        val yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
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
}