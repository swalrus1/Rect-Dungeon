package ru.swalrus.rectdungeon

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

// Все глобальные неизменяемые переменные
//
object Const {

    // Screen parameters
    val SCREEN_WIDTH : Int = Gdx.graphics.width
    val SCREEN_HEIGHT : Int = Gdx.graphics.height

    // Amount of some stuff
    val ROOM_SIZE = 7
    val QUCK_SLOTS = 6

    // Size, scale
    val MAP_BORDER : Float = 0.5f   // Относительная величина
    val MAP_SIZE : Float = minOf(SCREEN_HEIGHT - (2 * SCREEN_WIDTH).toFloat() / QUCK_SLOTS,
            SCREEN_WIDTH.toFloat())
    val IMG_SIZE: Int = 16
    val TILE_SIZE: Float = MAP_SIZE / (ROOM_SIZE + 2 * MAP_BORDER)
    val MAP_MARGIN_BOTTOM: Float = (SCREEN_HEIGHT - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER
    val MAP_MARGIN_LEFT : Float = (SCREEN_WIDTH - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER

    // Directions
    val CENTER = 0
    val TOP = 1
    val RIGHT = 2
    val BOTTOM = 3
    val LEFT = 4
    val dir2vec: Array<Vector2> = arrayOf(Vector2(0f, 0f), Vector2(0f, 1f),
            Vector2(1f, 0f), Vector2(0f, -1f), Vector2(-1f, 0f))

    // Animation parameters
    val MOVE_TIME = 0.4f

    var images : Map<String, Texture> = emptyMap()

    lateinit var FLOOR : Tile
    lateinit var WALL_LEFT : Tile
    lateinit var WALL_RIGHT : Tile
    lateinit var WALL_TOP : Tile
    lateinit var WALL_BOTTOM : Tile


    fun importImages() {

        images = mapOf(
                "FLOOR" to Texture("Floor.png"),
                "WALL_LEFT" to Texture("Wall_left.png"),
                "WALL_TOP" to Texture("Wall_top.png"),
                "WALL_RIGHT" to Texture("Wall_right.png"),
                "WALL_BOTTOM" to Texture("Wall_bottom.png"),
                "HUMAN" to Texture("Human_up.png")
        )

        FLOOR = Tile(images["FLOOR"]!!)
        WALL_LEFT = Tile(images["WALL_LEFT"]!!, false)
        WALL_RIGHT = Tile(images["WALL_RIGHT"]!!, false)
        WALL_TOP = Tile(images["WALL_TOP"]!!, false)
        WALL_BOTTOM = Tile(images["WALL_BOTTOM"]!!, false)
    }
}
