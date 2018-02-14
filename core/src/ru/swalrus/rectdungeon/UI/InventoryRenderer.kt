package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

class InventoryRenderer (val player: Player) {

    val MARGIN_LEFT = Const.INV_MARGIN_LEFT
    val MARGIN_BOTTOM = Const.INV_MARGIN_BOTTOM
    val background = Utils.getImg("inventory")
    val SCALE = Const.INV_SCALE
    val WIDTH = SCALE * Const.INV_BACKGROUND_WIDTH
    val HEIGHT = SCALE * Const.INV_BACKGROUND_HEIGHT

    var opened = false


    fun draw(batch: SpriteBatch) {
        if (opened) {
            batch.draw(background, MARGIN_LEFT, MARGIN_BOTTOM, WIDTH, HEIGHT, 0f, 1f, 1f, 0f)
        }
    }

    // Open/close
    fun switch() {
        opened = !opened
    }
}