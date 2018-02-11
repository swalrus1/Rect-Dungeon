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
    val INVENTORY_SIZE = 24
    val ARTEFACT_SLOTS = 3

    // Map render parameters
    val MAP_BORDER: Float = 0.5f   // Относительная величина
    val MAP_SIZE: Float = minOf(SCREEN_HEIGHT - (2 * SCREEN_WIDTH).toFloat() / QUCK_SLOTS,
            SCREEN_WIDTH.toFloat())
    val IMG_SIZE: Int = 16
    val TILE_SIZE: Float = MAP_SIZE / (ROOM_SIZE + 2 * MAP_BORDER)
    val MAP_MARGIN_BOTTOM: Float = (SCREEN_HEIGHT - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER
    val MAP_MARGIN_LEFT: Float = (SCREEN_WIDTH - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER

    // UI
    val STATUS_SCALE: Float = TILE_SIZE / IMG_SIZE
    val BOTTOM_SCALE: Float = TILE_SIZE / IMG_SIZE * 1.3f
    val HP_MARGIN: Float = 3.5f * STATUS_SCALE
    val UI_MAX_HP: Int = 8
    val BOTTOM_TILE_SIZE: Float = TILE_SIZE * 1.3f

    // Directions
    val CENTER = 'c'
    val TOP = 't'
    val RIGHT = 'r'
    val BOTTOM = 'b'
    val LEFT = 'l'
    val HORIZONTAL = 'h'
    val VERTICAL = 'v'
    val TOP_LEFT = 'k'
    val TOP_RIGHT = 'l'
    val BOTTOM_LEFT = 'm'
    val BOTTOM_RIGHT = 'n'

    // Animation parameters
    val MOVE_TIME = 0.25f
    val ATTACK_TIME = 0.25f
    val ATTACK_D_S = 0.5f
    val PUSH_TIME = 0.1f
    val PUSH_D_S = 0.4f
    val ROTATE_TIME = 0.3f      // Time to rotate in move

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
                "door_top" to Texture("Door.png"),
                "door_right" to Texture("Door_right.png"),
                "door_left" to Texture("Door_left.png"),
                "door_bottom" to Texture("Door_bottom.png"),
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
                "wait_icon" to Texture("Clock.png"),
                "short_sword" to Texture("Short_sword.png"),
                "yellow_area" to Texture("Yellow_area.png"),
                "gray_area" to Texture("Gray_area.png"),
                "rapier" to Texture("Rapier.png"),
                "skeleton" to Texture("Skeleton.png"),
                "lava" to Texture("Lava.png"),
                "lava_top" to Texture("Lava_top.png"),
                "lava_right" to Texture("Lava_right.png"),
                "lava_bottom" to Texture("Lava_bottom.png"),
                "lava_left" to Texture("Lava_left.png"),
                "lava_horizontal" to Texture("Lava_horizontal.png"),
                "lava_vertical" to Texture("Lava_vertical.png"),
                "lava_top_right" to Texture("Lava_top_right.png"),
                "lava_top_left" to Texture("Lava_top_left.png"),
                "lava_bottom_right" to Texture("Lava_bottom_right.png"),
                "lava_bottom_left" to Texture("Lava_bottom_left.png")
        )
    }
}
