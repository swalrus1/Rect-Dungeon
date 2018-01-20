package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Utils

class BottomPanel {

    val slot = Utils.getImg("bottom_slot")
    val waitIcon = Utils.getImg("wait_icon")
    val inventoryIcon = Utils.getImg("inventory_icon")
    val button = Utils.getImg("bottom_button")


    fun draw(batch: SpriteBatch) {

        batch.draw(button, 0f, 0f, Const.TILE_SIZE, Const.TILE_SIZE, 1f, 1f, 0f, 0f)
        batch.draw(waitIcon, 0f, 0f, Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)

        batch.draw(button, Const.SCREEN_WIDTH - Const.TILE_SIZE, 0f,
                Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
        batch.draw(inventoryIcon, Const.SCREEN_WIDTH - Const.TILE_SIZE, 0f,
                Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
    }
}