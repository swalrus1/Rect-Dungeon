package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Creature (x : Int, y : Int, img : Texture, room: Room) {

    private var active : Boolean = true

    var x : Int = x
    var y : Int = y
    var img : Texture = img


    init {
        room.addCreature(this)
    }

    fun isActive() : Boolean {
        return active
    }

    fun move (direction : Int) {

    }

    fun draw (batch: SpriteBatch) {
        val xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
        val yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
        batch.draw(this.img, xPos, yPos, Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
    }

    fun makeTurn() {

    }

    // + Fun animate()
}