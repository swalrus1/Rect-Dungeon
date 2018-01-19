package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

object Utils {

    fun reverseDirection(direction : Int) : Int {

        when (direction) {
            Const.TOP -> return Const.BOTTOM
            Const.BOTTOM -> return Const.TOP
            Const.RIGHT -> return Const.LEFT
            Const.LEFT -> return Const.RIGHT
            Const.CENTER -> return Const.CENTER
            else -> return Const.CENTER
        }
    }

    fun dir2vec (direction: Int) : Vector2 {
        when (direction) {
            Const.TOP -> return Vector2(0f, 1f)
            Const.BOTTOM -> return Vector2(0f, -1f)
            Const.RIGHT -> return Vector2(1f, 0f)
            Const.LEFT -> return Vector2(-1f, 0f)
            else -> return Vector2(0f, 0f)
        }
    }

    fun randomDirection() : Int {
        return MathUtils.random(1, 4)
    }

    fun getImg(name: String, direction: Int = Const.CENTER) : Texture {
        when (name) {
            "wall" -> {
                when (direction) {
                    Const.TOP -> return Const.images["wall_top"]!!
                    Const.BOTTOM -> return Const.images["wall_bottom"]!!
                    Const.RIGHT -> return Const.images["wall_right"]!!
                    Const.LEFT -> return Const.images["wall_left"]!!
                }
            }
            "door" -> {
                return Const.images["door"]!!
            }
            else -> return Const.images[name]!!
        }
        return Const.images["empty"]!!
    }
}