package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Gdx.app
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

class InventoryRenderer (val player: Player) {

    val MARGIN_LEFT = Const.INV_MARGIN_LEFT
    val MARGIN_BOTTOM = Const.INV_MARGIN_BOTTOM
    val PADDING = Const.INV_PADDING * Const.INV_SCALE
    val CELL_SIZE = Const.INV_CELL_SIZE * Const.INV_SCALE
    val STEP = (Const.INV_CELL_SIZE - 1) * Const.INV_SCALE
    val background = Utils.getImg("inventory")
    val SCALE = Const.INV_SCALE
    val WIDTH = SCALE * Const.INV_BACKGROUND_WIDTH
    val HEIGHT = SCALE * Const.INV_BACKGROUND_HEIGHT
    val SCREEN_HEIGHT = Const.SCREEN_HEIGHT
    val ROW = Const.INVENTORY_ROW_SIZE

    var opened = false


    fun draw(batch: SpriteBatch) {
        if (opened) {
            batch.draw(background, MARGIN_LEFT, MARGIN_BOTTOM, WIDTH, HEIGHT, 0f, 1f, 1f, 0f)

            var i = 0

            for (item in player.inventory) {
                if (item != null) {
                    batch.draw(item.img, MARGIN_LEFT + PADDING + (i % ROW) * STEP,
                            SCREEN_HEIGHT - MARGIN_BOTTOM - PADDING - (i / ROW + 1) * STEP - 1 * SCALE,
                            CELL_SIZE, CELL_SIZE, 0f, 1f, 1f, 0f)
                    // TODO: Y клетки
                    i++
                }
            }
        }
    }

    // Open/close
    fun switch(action: Char = 'n') {
        when (action) {
            'o' -> opened = true
            'c' -> opened = false
            'n' -> opened = !opened
        }
    }

    fun press(x: Int, y: Int) {
        press(y * ROW + x)
    }

    fun press(i: Int) {
        app.log("Info", "Pressed $i slot")
    }
}