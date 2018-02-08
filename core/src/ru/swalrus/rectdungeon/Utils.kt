package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Game.*
import kotlin.math.abs

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

    fun vec2dir (vector: Vector2) : Int {
        if (vector.x > 0) {
            return Const.RIGHT
        } else if (vector.x < 0) {
            return Const.LEFT
        } else if (vector.y > 0) {
            return Const.TOP
        } else if (vector.y < 0) {
            return Const.BOTTOM
        } else {
            return Const.CENTER
        }
    }

    fun randomDirection() : Int {
        return MathUtils.random(1, 4)
    }

    fun getDirection(x: Float, y: Float) : Int {
        return if (abs(x) > abs(y)) {
            if (x > 0) {
                Const.RIGHT
            } else {
                Const.LEFT
            }
        } else {
            if (y > 0) {
                Const.TOP
            } else {
                Const.BOTTOM
            }
        }
    }

    fun getDirection(x: Int, y: Int) : Int {
        return getDirection(x.toFloat(), y.toFloat())
    }

    fun getTile(id: Int) : Tile {
        return when (id) {
            0 -> Floor()
            1 -> Lava()
            else -> Floor()
        }
    }

    fun getTileID(name: String) : Int {
        return when (name) {
            "floor" -> 0
            "lava" -> 1
            else -> 0
        }
    }

    fun isTarget(creature: Creature?, target: Char) : Boolean {
        return when (target) {
            'a' -> true
            'c' -> (creature != null)
            'p' -> (creature != null) and (creature is Player)
            'e' -> (creature != null) and (creature !is Player)
            else -> false
        }
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
                when (direction) {
                    Const.TOP -> return Const.images["door_top"]!!
                    Const.BOTTOM -> return Const.images["door_bottom"]!!
                    Const.RIGHT -> return Const.images["door_right"]!!
                    Const.LEFT -> return Const.images["door_left"]!!
                }
            }
            else -> return Const.images[name]!!
        }
        return Const.images["empty"]!!
    }
}