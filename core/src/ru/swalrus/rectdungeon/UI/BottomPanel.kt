package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.InputListener
import ru.swalrus.rectdungeon.Utils

class BottomPanel (player: Player, listener: InputListener) {

    val slot = Utils.getImg("bottom_slot")
    val waitIcon = Utils.getImg("wait_icon")
    val inventoryIcon = Utils.getImg("inventory_icon")
    val button = Utils.getImg("bottom_button")
    val size = Const.BOTTOM_TILE_SIZE
    var activeHandID: Int = 0

    var leftHandSlot: ItemButton = ItemButton(Const.SCREEN_WIDTH - 3.5f * Const.BOTTOM_TILE_SIZE,
            0f, 1, Const.BOTTOM_TILE_SIZE, player.leftHand, this, listener)
    var rightHandSlot: ItemButton = ItemButton(Const.SCREEN_WIDTH - 2.5f * Const.BOTTOM_TILE_SIZE,
            0f, 2, Const.BOTTOM_TILE_SIZE, player.rightHand, this, listener)


    fun draw(batch: SpriteBatch) {

        batch.draw(button, 0f, 0f, size, size, 1f, 1f, 0f, 0f)
        batch.draw(waitIcon, 0f, 0f, size, size, 0f, 1f, 1f, 0f)

        batch.draw(button, Const.SCREEN_WIDTH - size, 0f,
                size, size, 0f, 1f, 1f, 0f)
        batch.draw(inventoryIcon, Const.SCREEN_WIDTH - size, 0f,
                size, size, 0f, 1f, 1f, 0f)

        leftHandSlot.draw(batch)
        rightHandSlot.draw(batch)
    }
}