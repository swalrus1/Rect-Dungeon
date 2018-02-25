package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Items.Item
import ru.swalrus.rectdungeon.Utils

class ItemCard {

    val background: Texture = Utils.getImg("card_background")
    val MARGIN_BOTTOM: Float = Const.CARD_MARGIN_BOTTOM
    val MARGIN_LEFT: Float = Const.CARD_MARGIN_LEFT
    val WIDTH: Float = Const.CARD_WIDTH * Const.CARD_SCALE
    val HEIGHT: Float = Const.CARD_HEIGHT * Const.CARD_SCALE
    val IMG_SIZE: Float = Const.CARD_IMG_SiZE * Const.CARD_SCALE
    val CONTENT_MARGIN_LEFT: Float = Const.CARD_PADDING * Const.CARD_SCALE + MARGIN_LEFT
    val IMG_Y: Float = MARGIN_BOTTOM + HEIGHT - IMG_SIZE
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
                    Const.SCREEN_WIDTH - 2 * CONTENT_MARGIN_LEFT, Align.topLeft, true)
        }
    }

    // button - { Throw, Use }
    fun press(button: Char) {

    }
}