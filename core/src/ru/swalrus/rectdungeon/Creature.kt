package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Gdx.graphics
import com.badlogic.gdx.math.MathUtils.random
import kotlin.math.exp

open class Creature (x: Int, y: Int, img: Texture, room: Room) {

    private var active : Boolean = true         // Is not sleeping
    var ready : Boolean = true                  // Is ready to make next turn

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


    fun makeTurn() {
        move(random(1, 4))
    }

    fun isActive() : Boolean {
        return active
    }


    private fun update() {
        // Move creature
        if (!moveDir.isZero) {
            // Check if creature has reached the destination
            if (dTime >= Const.MOVE_TIME) {
                x += moveDir.x.toInt()
                y += moveDir.y.toInt()
                moveDir = Vector2.Zero
                align()
                endAnim()
            } else {
                align()
                dPos = Vector2(moveDir)
                dPos.scl(Const.TILE_SIZE * moveFun(dTime))
                sprite.translate(dPos.x, dPos.y)
                dTime += graphics.deltaTime
            }
        }
    }

    private fun move (direction : Int) {
        moveDir = Const.dir2vec[direction]
        val tile = room.getTile(x + moveDir.x.toInt(), y + moveDir.y.toInt())
        if (tile.passable) {
            startAnim()
            dTime = 0f
        } else {
            moveDir = Vector2.Zero
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

    private fun endAnim() {
        ready = true
    }
}