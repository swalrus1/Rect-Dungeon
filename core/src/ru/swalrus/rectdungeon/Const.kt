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
    const val ROOM_SIZE = 7
    const val QUICK_SLOTS = 6
    const val MAX_AP = 2
    const val INVENTORY_SIZE = 23
    const val ARTIFACT_SLOTS = 3

    // Map render parameters
    const val MAP_BORDER: Float = 0.5f   // Относительная величина
    val MAP_SIZE: Float = minOf(SCREEN_HEIGHT - (2 * SCREEN_WIDTH).toFloat() / QUICK_SLOTS,
            SCREEN_WIDTH.toFloat())
    val IMG_SIZE: Int = 16
    val TILE_SIZE: Float = MAP_SIZE / (ROOM_SIZE + 2 * MAP_BORDER)
    val MAP_MARGIN_BOTTOM: Float = (SCREEN_HEIGHT - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER
    val MAP_MARGIN_LEFT: Float = (SCREEN_WIDTH - MAP_SIZE) / 2 - TILE_SIZE * MAP_BORDER

    // UI
    val STATUS_SCALE: Float = TILE_SIZE / IMG_SIZE
    val BOTTOM_SCALE: Float = TILE_SIZE / IMG_SIZE * 1.3f
    val HP_MARGIN: Float = 3.5f * STATUS_SCALE
    const val UI_MAX_HP: Int = 8
    const val INVENTORY_ROW_SIZE: Int = 4
    val BOTTOM_TILE_SIZE: Float = TILE_SIZE * 1.3f
    const val INV_BACKGROUND_WIDTH: Int = 65
    const val INV_BACKGROUND_HEIGHT: Int = 80
    val INV_MARGIN_LEFT: Float = SCREEN_WIDTH.toFloat() / 10f
    val INV_SCALE: Float = (SCREEN_WIDTH.toFloat() - INV_MARGIN_LEFT * 2) / INV_BACKGROUND_WIDTH
    const val INV_PADDING: Int = 2
    val INV_MARGIN_BOTTOM: Float = (SCREEN_HEIGHT.toFloat() - INV_BACKGROUND_HEIGHT * INV_SCALE) / 2
    // TODO: margin left

    // Directions
    const val CENTER = 'c'
    const val TOP = 't'
    const val RIGHT = 'r'
    const val BOTTOM = 'b'
    const val LEFT = 'l'

    // Animation parameters
    const val MOVE_TIME = 0.25f
    const val ATTACK_TIME = 0.25f
    const val ATTACK_D_S = 0.5f
    const val PUSH_TIME = 0.1f
    const val PUSH_D_S = 0.4f
    const val ROTATE_TIME = 0.3f      // Time to rotate in move

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
                "lava_bottom_left" to Texture("Lava_bottom_left.png"),
                "inventory" to Texture("Inventory_background.png")
        )
    }
}
