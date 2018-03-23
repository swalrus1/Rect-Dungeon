package ru.swalrus.rectdungeon

import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Items.Weapon
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

    fun moveToPlayer(creature: Creature) {
        creature.move(playerDirection(creature.x, creature.y))
    }

    fun attackPlayerIfNear(creature: Creature, weapon: Weapon) : Boolean {
        val dir = AI.playerNearDirection(creature.x, creature.y)
        if (dir != 'n') {
            val t = Utils.dir2vec(dir)
            val nx = creature.x + t.x.roundToInt()
            val ny = creature.y + t.y.roundToInt()
            weapon.cast(nx, ny, creature, creature.room.getCreatureAt(nx, ny))
            return true
        } else {
            return false
        }
    }
}