package ru.swalrus.rectdungeon

import com.badlogic.gdx.Gdx.app
import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.UI.BottomPanel
import ru.swalrus.rectdungeon.UI.InventoryRenderer
import ru.swalrus.rectdungeon.UI.ItemCard

class InputListener (val player: Player) : GestureListener {

    // Areas which must be listened for clicks
    var touchAreas: MutableList<Pair<Rectangle, () -> Unit>> = mutableListOf()
    // The area of the opened inventory
    val inventotyArea: Rectangle = Rectangle(Const.INV_MARGIN_LEFT, Const.INV_MARGIN_BOTTOM,
            Const.SCREEN_WIDTH - 2 * Const.INV_MARGIN_LEFT, Const.SCREEN_HEIGHT - 2 * Const.INV_MARGIN_BOTTOM)
    // The area of an opened card
    val cardArea: Rectangle = Rectangle(Const.CARD_MARGIN_LEFT, Const.CARD_MARGIN_BOTTOM,
            Const.SCREEN_WIDTH - 2 * Const.CARD_MARGIN_LEFT, Const.SCREEN_HEIGHT - 2 * Const.CARD_MARGIN_BOTTOM)
    // Parameters of the inventory
    val INV_MARGIN_TOP: Float = Const.INV_MARGIN_BOTTOM + Const.INV_PADDING * Const.INV_SCALE
    val INV_MARGIN_LEFT: Float = Const.INV_MARGIN_LEFT + Const.INV_PADDING * Const.INV_SCALE
    val INV_CELL_SIZE: Float = Const.INV_CELL_SIZE * Const.INV_SCALE
    val INV_ROW: Int = Const.INVENTORY_ROW_SIZE
    val INV_COLUMN: Int = (Const.INVENTORY_SIZE + 1) / Const.INVENTORY_ROW_SIZE

    lateinit var bottomPanel: BottomPanel
    lateinit var inventory: InventoryRenderer
    lateinit var card: ItemCard


    // Add a new area to the array of listed areas
    // f - a function that must be called when area is clicked to
    fun addArea(x1: Float, y1: Float, width: Float, height: Float, f: () -> Unit) {
        touchAreas.add(Pair(Rectangle(x1, y1, width, height), f))
    }


    // Swipe, then release
    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (!inventory.opened) {
            player.swipe(Utils.getDirection(velocityX, -velocityY))
            return true
        }
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        return false
    }

    override fun pinchStop() {

    }

    // Tap, then release
    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        // If player is waiting for input
        if (!player.inAnim() and !player.ready) {

            if (card.opened) {
                if (cardArea.contains(x, Const.SCREEN_HEIGHT - y)) {
                    var area = Rectangle(card.BUTTON_MARGIN_LEFT, card.BUTTON_MARGIN_BOTTOM, card.BUTTON_WIDTH, card.BUTTON_HEIGHT)
                    if (area.contains(x, Const.SCREEN_HEIGHT - y)) {
                        card.press('l')
                    }
                    area.x += card.SPACE_BETWEEN + card.BUTTON_WIDTH
                    if (area.contains(x, Const.SCREEN_HEIGHT - y)) {
                        card.press('r')
                    }
                } else {
                    card.close()
                }
                return true

            } else if (inventory.opened) {
                // Check if the tap is in inventory area
                if (inventotyArea.contains(x, Const.SCREEN_HEIGHT - y)) {
                    val invX: Int = ((x - INV_MARGIN_LEFT) / INV_CELL_SIZE).toInt()
                    val invY: Int = ((y - INV_MARGIN_TOP) / INV_CELL_SIZE).toInt()
                    if ((invX >= 0) and (invX < INV_ROW) and
                            (invY >= 0) and (invY < INV_COLUMN)) {
                        inventory.press(invX, invY)
                    }
                } else {
                    inventory.close()
                }
                return true
            } else {
                // Handle taps on the map
                for (i in 0 until touchAreas.size) {
                    if (touchAreas[i].first.contains(x, Const.SCREEN_HEIGHT - y)) {
                        touchAreas[i].second()
                        return true
                    }
                }
                return bottomPanel.tapOnMap(x, Const.SCREEN_HEIGHT - y)
            }
        } else {
            return false
        }
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun pinch(initialPointer1: Vector2?, initialPointer2: Vector2?, pointer1: Vector2?, pointer2: Vector2?): Boolean {
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return false
    }
}