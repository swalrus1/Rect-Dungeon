package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.InputListener
import ru.swalrus.rectdungeon.Items.Item
import ru.swalrus.rectdungeon.Utils

class ItemButton(val x: Float, val y: Float, listener: InputListener) {

    var pressed: Boolean = false
    var item: Item? = null
    val shadow: Texture = Utils.getImg("button_shadow")
    val slot: Texture = Utils.getImg("bottom_slot")


    init {
        listener.addArea(x, y, Const.BOTTOM_TILE_SIZE, Const.BOTTOM_TILE_SIZE, { onTouch() })
    }


    fun draw(batch: SpriteBatch) {
        if (pressed or (item == null)) {
            batch.draw(slot, x, y, Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
        } else {
            batch.draw(shadow, x, y, Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
            batch.draw(item!!.img, x, y + 1 * Const.STATUS_SCALE, Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
            batch.draw(slot, x, y + 1 * Const.STATUS_SCALE, Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
        }
    }

    fun onTouch() {
        if (item != null) {
            pressed = !pressed
        }
    }
}