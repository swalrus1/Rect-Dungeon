package ru.swalrus.rectdungeon

import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.UI.BottomPanel
import ru.swalrus.rectdungeon.UI.InventoryRenderer
import kotlin.math.abs

class InputListener (val player: Player, val inventory: InventoryRenderer) : GestureListener {

    var touchAreas: MutableList<Pair<Rectangle, () -> Unit>> = mutableListOf()
    val inventotyArea: Rectangle = Rectangle(Const.INV_MARGIN_LEFT, Const.INV_MARGIN_BOTTOM,
            Const.SCREEN_WIDTH - 2 * Const.INV_MARGIN_LEFT, Const.SCREEN_HEIGHT - 2 * Const.INV_MARGIN_BOTTOM)
    lateinit var bottomPanel: BottomPanel


    fun addArea(x1: Float, y1: Float, width: Float, height: Float, f: () -> Unit) {
        touchAreas.add(Pair(Rectangle(x1, y1, width, height), f))
    }


    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        player.swipe(Utils.getDirection(velocityX, -velocityY))
        return true
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        return false
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        return false
    }

    override fun pinchStop() {

    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        // If player is waiting for input
        if (!player.inAnim() and !player.ready) {
            // If inventory is opened
            if (inventory.opened) {
                // Check if the tap is in inventory area
                if (inventotyArea.contains(x, Const.SCREEN_HEIGHT - y)) {

                    return true
                } else {
                    inventory.switch()
                    return true
                }
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