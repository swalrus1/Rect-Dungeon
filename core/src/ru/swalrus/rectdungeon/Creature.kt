package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch

open class Creature (x: Int, y: Int, img: Texture, room: Room) {

    private var active : Boolean = true         // Is not sleeping
    var ready : Boolean = true                  // Is ready to make next turn

    var x: Int = x
    var y: Int = y
    private var sprite: Sprite = Sprite(img)


    init {
        // Add creature to the queue
        room.addCreature(this)

        // Set position of sprite
        val xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
        val yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
        sprite.setPosition(xPos, yPos)
        sprite.setSize(Const.TILE_SIZE, Const.TILE_SIZE)
    }

    fun isActive() : Boolean {
        return active
    }

    fun move (direction : Int) {

    }

    fun draw (batch: SpriteBatch) {
        sprite.draw(batch)
    }

    fun makeTurn() {

    }

    // + Fun animate()
}