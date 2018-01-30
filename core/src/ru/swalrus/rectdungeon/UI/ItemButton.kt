package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.InputListener
import ru.swalrus.rectdungeon.Items.Item
import ru.swalrus.rectdungeon.Utils

class ItemButton(val x: Float, val y: Float, val id: Int, var size: Float, var item: Item?, var panel: BottomPanel,
                 listener: InputListener) {

    val shadow: Texture = Utils.getImg("button_shadow")
    val slot: Texture = Utils.getImg("bottom_slot")


    init {
        listener.addArea(x, y, size, size, { onTouch() })
    }


    fun draw(batch: SpriteBatch) {
        if ((panel.activeHandID == id) or (item == null)) {
            batch.draw(slot, x, y, size, size, 0f, 1f, 1f, 0f)
            if (item != null) {
                batch.draw(item!!.img, x, y, size, size, 0f, 1f, 1f, 0f)
            }
        } else {
            batch.draw(shadow, x, y, size, size, 0f, 1f, 1f, 0f)
            batch.draw(slot, x, y + 1 * Const.STATUS_SCALE, size, size, 0f, 1f, 1f, 0f)
            batch.draw(item!!.img, x, y + 1 * Const.STATUS_SCALE, size, size, 0f, 1f, 1f, 0f)
        }
    }

    fun onTouch() {
        if (item != null) {
            if (panel.activeHandID == id) {
                panel.activeHandID = 0
            } else {
                panel.activeHandID = id
            }
        }
    }
}