package ru.swalrus.rectdungeon

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

// All global variables
//
object Const {

    // Screen parameters
    val SCREEN_WIDTH : Int = Gdx.graphics.width
    val SCREEN_HEIGHT : Int = Gdx.graphics.height

    // Amount of some stuff
    val ROOM_SIZE = 7
    val QUCK_SLOTS = 6
    val MAX_AP = 2

    // Map render parameters
    val MAP_BORDER : Float = 0.5f   // Относительная величина
    val MAP_SIZE : Float = minOf(SCREEN_HEIGHT - (2 * SCREEN_WIDTH).toFloat() / QUCK_SLOTS,
            SCREEN_WIDTH.toFloat())
    val IMG_SIZE: Int = 16
    val TILE_SIZE: Float = MAP_SIZE / (ROOM_SIZE + 2 * MAP_BORDER)
    val MAP_MARGIN_BOTTOM: Float = (SCREEN_HEIGHT - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER
    val MAP_MARGIN_LEFT : Float = (SCREEN_WIDTH - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER

    // UI
    val UI_SCALE: Float = TILE_SIZE / IMG_SIZE
    val HP_MARGIN: Float = 3.5f * UI_SCALE
    val UI_MAX_HP: Int = 8

    // Directions
    val CENTER = 0
    val TOP = 1
    val RIGHT = 2
    val BOTTOM = 3
    val LEFT = 4
    val dir2vec: Array<Vector2> = arrayOf(Vector2(0f, 0f), Vector2(0f, 1f),
            Vector2(1f, 0f), Vector2(0f, -1f), Vector2(-1f, 0f))

    // Animation parameters
    val MOVE_TIME = 0.25f

    var images : Map<String, Texture> = emptyMap()

    lateinit var emptyTile: Tile

    fun randomDirection() : Int {
        return MathUtils.random(1, 4)
    }

    fun getWallImg(dir: Int) : Texture {
        when (dir) {
            TOP -> return images["WALL_TOP"]!!
            BOTTOM -> return images["WALL_BOTTOM"]!!
            RIGHT -> return images["WALL_RIGHT"]!!
            LEFT -> return images["WALL_LEFT"]!!
        }
        return images["EMPTY"]!!
    }

    fun getDoorImg(dir: Int) : Texture {
        return images["DOOR"]!!
    }

    // TODO: Add getImg(name, dir) method

    fun importImages() {

        images = mapOf(
                "FLOOR" to Texture("Floor.png"),
                "WALL_LEFT" to Texture("Wall_left.png"),
                "WALL_TOP" to Texture("Wall_top.png"),
                "WALL_RIGHT" to Texture("Wall_right.png"),
                "WALL_BOTTOM" to Texture("Wall_bottom.png"),
                "HUMAN" to Texture("Human_up.png"),
                "EMPTY" to Texture("Empty.png"),
                "DOOR" to Texture("Door.png"),
                "AP_0" to Texture("AP_empty.png"),
                "AP_1" to Texture("AP_half.png"),
                "AP_2" to Texture("AP_full.png"),
                "UI_TILE_TOP" to Texture("UI_tile_top.png"),
                "HEART" to Texture("Heart.png"),
                "HEART_EMPTY" to Texture("Heart_empty.png"),
                "MENU_BUTTON" to Texture("Menu_button.png")
        )

        emptyTile = Tile(images["EMPTY"]!!, passable = false)
    }
}
