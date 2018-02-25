package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.InputListener
import ru.swalrus.rectdungeon.Items.Item
import ru.swalrus.rectdungeon.Utils

class ItemCard (listener: InputListener) {

    val background: Texture = Utils.getImg("card_background")
    val MARGIN_BOTTOM: Float = Const.CARD_MARGIN_BOTTOM
    val MARGIN_LEFT: Float = Const.CARD_MARGIN_LEFT
    val WIDTH: Float = Const.CARD_WIDTH * Const.CARD_SCALE
    val HEIGHT: Float = Const.CARD_HEIGHT * Const.CARD_SCALE

    lateinit var item: Item
    var opened: Boolean = false


    init {

    }


    fun open(item: Item) {
        this.item = item
        opened = true
    }

    fun close() {
        opened = false
    }

    fun draw(batch: SpriteBatch) {
        if (opened) {
            batch.draw(background, MARGIN_LEFT, MARGIN_BOTTOM, WIDTH, HEIGHT, 0f, 1f, 1f, 0f)
        }
    }

    // button - { Throw, Use }
    fun press(button: Char) {

    }
}