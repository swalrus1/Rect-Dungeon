package ru.swalrus.rectdungeon

import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Game.Player
import kotlin.math.abs

class InputListener (player: Player) : GestureListener {

    var player: Player = player
    var touchAreas: MutableList<Pair<Rectangle, () -> Unit>> = mutableListOf()


    fun addArea(x1: Float, y1: Float, width: Float, height: Float, f: () -> Unit) {
        touchAreas.add(Pair(Rectangle(x1, y1, width, height), f))
    }


    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        if (abs(velocityX) > abs(velocityY)) {
            if (velocityX > 0) {
                player.swipe(Const.RIGHT)
            } else {
                player.swipe(Const.LEFT)
            }
        } else {
            if (velocityY > 0) {
                player.swipe(Const.BOTTOM)
            } else {
                player.swipe(Const.TOP)
            }
        }
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
        for (i in 0 until touchAreas.size) {
            if (touchAreas[i].first.contains(x, Const.SCREEN_HEIGHT - y)) {
                touchAreas[i].second()
                return true
            }
        }
        return false
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