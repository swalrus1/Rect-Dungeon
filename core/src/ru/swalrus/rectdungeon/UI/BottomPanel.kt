package ru.swalrus.rectdungeon.UI

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.InputListener
import ru.swalrus.rectdungeon.Utils

class BottomPanel (val player: Player, listener: InputListener, val inventory: InventoryRenderer) {

    val slot = Utils.getImg("bottom_slot")
    val waitIcon = Utils.getImg("wait_icon")
    val inventoryIcon = Utils.getImg("inventory_icon")
    val button = Utils.getImg("bottom_button")
    val size = Const.BOTTOM_TILE_SIZE
    var activeHandID: Int = 0

    var leftHandSlot: ItemButton = ItemButton(Const.SCREEN_WIDTH - 3.5f * Const.BOTTOM_TILE_SIZE,
            0f, 1, Const.BOTTOM_TILE_SIZE, player.leftHand, this, player, listener)
    var rightHandSlot: ItemButton = ItemButton(Const.SCREEN_WIDTH - 2.5f * Const.BOTTOM_TILE_SIZE,
            0f, 2, Const.BOTTOM_TILE_SIZE, player.rightHand, this, player, listener)


    init {
        listener.addArea(Const.SCREEN_WIDTH - size, 0f, size, size, { inventory.switch() })
        listener.addArea(0f, 0f, size, size, {
            player.AP = 0
            player.endTurn() })
    }


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

    fun tapOnMap(x: Float, y: Float) : Boolean {
        var xPos: Float
        var yPos: Float
        var rect: Rectangle
        for (mapX in 0 until Const.ROOM_SIZE)
            for (mapY in 0 until Const.ROOM_SIZE) {
                if (player.room.yellowArea[mapX][mapY]) {
                    xPos = (mapX+1) * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
                    yPos = (mapY+1) * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
                    rect = Rectangle(xPos, yPos, Const.TILE_SIZE, Const.TILE_SIZE)
                    if (rect.contains(x, y)) {
                        player.cast(activeHandID, mapX+1, mapY+1)
                        activeHandID = 0
                        player.room.resetYellowArea()
                        return true
                    }
                }
            }
        return false
    }
}