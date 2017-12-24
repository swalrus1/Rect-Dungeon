package ru.swalrus.rectdungeon

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

object Const {

    val ROOM_SIZE = 7

    val MAP_MARGIN_BOTTOM: Int = (Gdx.graphics.height - Gdx.graphics.width) / 2
    val IMG_SIZE: Int = 16
    val TILE_SIZE: Float = (Gdx.graphics.width).toFloat() / (ROOM_SIZE).toFloat()

    val CENTER = 0
    val TOP = 1
    val RIGHT = 2
    val BOTTOM = 3
    val LEFT = 4

    var images : Map<String, Texture> = emptyMap()

    lateinit var FLOOR : Tile


    fun importImages() {

        images = mapOf(
                "FLOOR" to Texture("Floor.png")
        )

        FLOOR = Tile(images["FLOOR"]!!)
    }
}
