package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Items.Item
import ru.swalrus.rectdungeon.Utils

class ItemCard (val player: Player) {

    val background: Texture = Utils.getImg("card_background")
    val button: Texture = Utils.getImg("card_button")
    val SCALE = Const.CARD_SCALE
    val MARGIN_BOTTOM: Float = Const.CARD_MARGIN_BOTTOM
    val MARGIN_LEFT: Float = Const.CARD_MARGIN_LEFT
    val WIDTH: Float = Const.CARD_WIDTH * Const.CARD_SCALE
    val HEIGHT: Float = Const.CARD_HEIGHT * Const.CARD_SCALE
    val IMG_SIZE: Float = Const.CARD_IMG_SiZE * Const.CARD_SCALE
    val CONTENT_MARGIN_LEFT: Float = Const.CARD_PADDING_LEFT * Const.CARD_SCALE + MARGIN_LEFT
    val CONTENT_WIDTH: Float = WIDTH - (Const.CARD_PADDING_RIGHT + Const.CARD_PADDING_LEFT) * Const.CARD_SCALE
    val IMG_Y: Float = MARGIN_BOTTOM + HEIGHT - IMG_SIZE
    val BUTTON_MARGIN_BOTTOM = Const.BUTTON_MARGIN_BOTTOM * SCALE + MARGIN_BOTTOM
    val BUTTON_WIDTH = Const.BUTTON_WIDTH * SCALE
    val BUTTON_HEIGHT = Const.BUTTON_HEIGHT * SCALE
    val SPACE_BETWEEN = Const.BUTTON_SPACE_BETWEEN * SCALE
    val BUTTON_MARGIN_LEFT = (WIDTH - 2 * BUTTON_WIDTH - SPACE_BETWEEN) / 2 + MARGIN_LEFT
    lateinit var text: String

    lateinit var item: Item
    var opened: Boolean = false


    init {

    }


    fun open(item: Item) {
        this.item = item
        opened = true
        text = item.getDescription()
    }

    fun close() {
        opened = false
    }

    fun draw(batch: SpriteBatch) {
        if (opened) {
            batch.draw(background, MARGIN_LEFT, MARGIN_BOTTOM, WIDTH, HEIGHT, 0f, 1f, 1f, 0f)
            batch.draw(item.bigImage, MARGIN_LEFT + Const.CARD_IMG_MARGIN_LEFT, IMG_Y,
                    IMG_SIZE, IMG_SIZE, 0f, 1f, 1f, 0f)
            Const.headerFont.draw(batch, item.name, CONTENT_MARGIN_LEFT, IMG_Y)
            Const.cardFont.draw(batch, text, CONTENT_MARGIN_LEFT, IMG_Y - Const.CARD_HEADER_HEIGHT,
                    CONTENT_WIDTH, Align.topLeft, true)
            // Buttons
            batch.draw(button, BUTTON_MARGIN_LEFT, BUTTON_MARGIN_BOTTOM,
                    BUTTON_WIDTH, BUTTON_HEIGHT, 0f, 1f, 1f, 0f)
            batch.draw(button, BUTTON_MARGIN_LEFT + BUTTON_WIDTH + SPACE_BETWEEN, BUTTON_MARGIN_BOTTOM,
                    BUTTON_WIDTH, BUTTON_HEIGHT, 0f, 1f, 1f, 0f)
            // Buttons' text
            // TODO make "throw" const
            Const.cardButtonFont.draw(batch, "Throw", BUTTON_MARGIN_LEFT,
                    BUTTON_MARGIN_BOTTOM + BUTTON_HEIGHT - 1.5f * SCALE, BUTTON_WIDTH, Align.center, false)
            Const.cardButtonFont.draw(batch, item.actionName, BUTTON_MARGIN_LEFT + BUTTON_WIDTH + SPACE_BETWEEN,
                    BUTTON_MARGIN_BOTTOM + BUTTON_HEIGHT - 1.5f * SCALE, BUTTON_WIDTH, Align.center, false)
        }
    }

    // button - { Throw, Use }
    fun press(button: Char) {
        when (button) {
            'u' -> item.use(player)
        }
        close()
    }
}