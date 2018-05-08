package ru.swalrus.rectdungeon

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import kotlin.math.min

// All global constants
//
object Const {

    // Screen parameters
    val SCREEN_WIDTH: Int = Gdx.graphics.width
    val SCREEN_HEIGHT: Int = Gdx.graphics.height

    // Colors
    val WHITE: Color = Color(0.918f, 0.918f, 0.918f, 1f)
    val YELLOW: Color = Color(0.95f, 0.84f, 0.4f, 1f)
    val GREEN: Color = Color(0.56f, 0.93f, 0.56f, 1f)
    val RED: Color = Color(0.914f, 0.341f, 0.247f, 1f)

    // Amount of some stuff
    const val ROOM_SIZE = 7
    const val QUICK_SLOTS = 6
    const val MAX_AP = 2
    const val INVENTORY_SIZE = 19
    const val ARTIFACT_SLOTS = 3

    // Generator parameters
    const val COOL_LOOT_RATE: Int = 2
    const val LOOT_RATE: Int = 1

    // Font scale
    const val damageScale: Float = 1.4f
    const val headerScale: Float = 2.8f
    const val cardFontScale: Float = 1.6f
    const val cardButtonFontScale: Float = 2.4f

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
    const val INV_CELL_SIZE: Int = 16
    // TODO: margin left

    const val INDICATOR_OFFSET_Y: Float = 1.2f
    const val INDICATOR_OFFSET_X: Float = -0.2f

    const val CARD_WIDTH: Int = 65
    const val CARD_HEIGHT: Int = 100
    const val CARD_PADDING_LEFT: Int = 8
    const val CARD_PADDING_RIGHT: Int = 4
    const val CARD_IMG_SiZE: Int = 40
    const val CARD_MIN_MARGIN: Float = 0.1f
    // TODO card_max_width
    val CARD_SCALE: Float = min(SCREEN_HEIGHT / CARD_HEIGHT, SCREEN_WIDTH / CARD_WIDTH) * (1 - 2 * CARD_MIN_MARGIN)
    val CARD_MARGIN_BOTTOM: Float = (SCREEN_HEIGHT - CARD_HEIGHT * CARD_SCALE) / 2
    val CARD_MARGIN_LEFT: Float = (SCREEN_WIDTH - CARD_WIDTH * CARD_SCALE) / 2
    val CARD_IMG_MARGIN_LEFT: Float = (CARD_WIDTH - CARD_IMG_SiZE) * CARD_SCALE / 2
    val CARD_HEADER_HEIGHT: Float = headerScale * CARD_SCALE * 6f

    const val BUTTON_WIDTH: Int = 24
    const val BUTTON_HEIGHT: Int = 9
    const val BUTTON_MARGIN_BOTTOM: Int = 4
    const val BUTTON_SPACE_BETWEEN: Int = 6
    const val BUTTON_SCALE: Float = 0.5f

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
    const val ROTATE_TIME = 0.3f            // Time to rotate in move
    const val INDICATOR_TIME = 0.3f
    const val INDICATOR_D_S = 0.3f
    const val EXTRA_SLOT_BLINK_TIME = 0.8f
    const val THROW_TIME = 0.1f               // Time required to pass 1 tile
    const val THROW_SCALE = 0.8f

    lateinit var indicatorFont: BitmapFont
    lateinit var headerFont: BitmapFont
    lateinit var cardFont: BitmapFont
    lateinit var cardButtonFont: BitmapFont

    var images : Map<String, Texture> = emptyMap()

    fun load() {

        indicatorFont = BitmapFont(Gdx.files.internal("tight_pixel.fnt"))
        indicatorFont.data.setScale(CARD_SCALE / 10 * damageScale)
        indicatorFont.color = RED

        headerFont = BitmapFont(Gdx.files.internal("tight_pixel.fnt"))
        headerFont.data.setScale(CARD_SCALE / 10 * headerScale)
        headerFont.color = WHITE

        cardFont = BitmapFont(Gdx.files.internal("tight_pixel.fnt"))
        cardFont.data.setScale(CARD_SCALE / 10 * cardFontScale)
        cardFont.color = WHITE

        cardButtonFont = BitmapFont(Gdx.files.internal("tight_pixel.fnt"))
        cardButtonFont.data.setScale(CARD_SCALE / 10 * cardButtonFontScale)
        cardButtonFont.color = YELLOW

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
                "inventory" to Texture("Inventory_background.png"),
                "card_background" to Texture("Card_background.png"),
                "loot_icon" to Texture("Loot_icon.png"),
                "card_button" to Texture("Card_button.png"),
                "chest" to Texture("Chest.png"),
                "chest_open" to Texture("Chest_open.png"),
                "heal_potion" to Texture("Heal_potion_half.png"),
                "bow" to Texture("Bow.png"),
                "arrow_t" to Texture("Arrow_t.png"),
                "arrow_r" to Texture("Arrow_r.png"),
                "arrow_b" to Texture("Arrow_b.png"),
                "arrow_l" to Texture("Arrow_l.png"),
                "item_new" to Texture("Item_new.png"),
                "item_equipped" to Texture("Item_equipped.png"),
                "dagger" to Texture("Dagger.png"),
                "zombie" to Texture("Zombie.png"),
                "goblin" to Texture("Goblin.png"),
                "door_closed_top" to Texture("Door_closed_top.png"),
                "door_closed_right" to Texture("Door_closed_right.png"),
                "door_closed_left" to Texture("Door_closed_left.png"),
                "door_closed_bottom" to Texture("Door_closed_bottom.png"),
                "skeleton_archer" to Texture("Skeleton_archer.png"),
                "long_sword" to Texture("Long_sword.png"),
                "hammer" to Texture("Hammer.png"),
                "dagger" to Texture("Dagger.png"),
                "long_bow" to Texture("Long_bow.png")
        )
    }
}
