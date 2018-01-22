package ru.swalrus.rectdungeon

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

// All global constants
//
object Const {

    // Screen parameters
    val SCREEN_WIDTH: Int = Gdx.graphics.width
    val SCREEN_HEIGHT: Int = Gdx.graphics.height

    // Amount of some stuff
    val ROOM_SIZE = 7
    val QUCK_SLOTS = 6
    val MAX_AP = 2

    // Map render parameters
    val MAP_BORDER: Float = 0.5f   // Относительная величина
    val MAP_SIZE: Float = minOf(SCREEN_HEIGHT - (2 * SCREEN_WIDTH).toFloat() / QUCK_SLOTS,
            SCREEN_WIDTH.toFloat())
    val IMG_SIZE: Int = 16
    val TILE_SIZE: Float = MAP_SIZE / (ROOM_SIZE + 2 * MAP_BORDER)
    val MAP_MARGIN_BOTTOM: Float = (SCREEN_HEIGHT - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER
    val MAP_MARGIN_LEFT: Float = (SCREEN_WIDTH - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER

    // UI
    val UI_SCALE: Float = TILE_SIZE / IMG_SIZE
    val HP_MARGIN: Float = 3.5f * UI_SCALE
    val UI_MAX_HP: Int = 8
    val BOTTOM_TILE_SIZE: Float = TILE_SIZE * 1.3f

    // Directions
    val CENTER = 0
    val TOP = 1
    val RIGHT = 2
    val BOTTOM = 3
    val LEFT = 4

    // Animation parameters
    val MOVE_TIME = 0.25f

    var images : Map<String, Texture> = emptyMap()

    fun loadImages() {

        images = mapOf(
                "floor" to Texture("Floor.png"),
                "wall_left" to Texture("Wall_left.png"),
                "wall_top" to Texture("Wall_top.png"),
                "wall_right" to Texture("Wall_right.png"),
                "wall_bottom" to Texture("Wall_bottom.png"),
                "human" to Texture("Human_up.png"),
                "empty" to Texture("Empty.png"),
                "door" to Texture("Door.png"),
                "AP_0" to Texture("AP_empty.png"),
                "AP_1" to Texture("AP_half.png"),
                "AP_2" to Texture("AP_full.png"),
                "ui_tile_top" to Texture("UI_tile_top.png"),
                "heart" to Texture("Heart.png"),
                "heart_empty" to Texture("Heart_empty.png"),
                "menu_button" to Texture("Menu_button.png"),
                "bottom_button" to Texture("Bottom_button_right.png"),
                "bottom_slot" to Texture("Item_slot.png"),
                "button_shadow" to Texture("Button_shadow.png"),
                "inventory_icon" to Texture("Bag.png"),
                "wait_icon" to Texture("Clock.png")
        )
    }
}
