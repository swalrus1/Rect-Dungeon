package ru.swalrus.rectdungeon

import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Game.Player
import kotlin.math.abs
import kotlin.math.roundToInt

object AI {

    lateinit var player: Player

    fun playerDirection(x: Int, y: Int) : Char {
        return Utils.vec2dir(Vector2((player.x - x).toFloat(), (player.y - y).toFloat()))
    }

    // returns 'n' if player is not near, else returns direction
    fun playerNearDirection(x: Int, y: Int) : Char {
        return if ((player.x - x) * (player.x - x) + (player.y - y) * (player.y - y) <= 1) {
            Utils.vec2dir(Vector2((player.x - x).toFloat(), (player.y - y).toFloat()))
        } else {
            'n'
        }
    }
}