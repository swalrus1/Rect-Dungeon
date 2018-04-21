package ru.swalrus.rectdungeon

import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Items.Weapon
import kotlin.math.abs
import kotlin.math.roundToInt

object AI {

    lateinit var player: Player

    // Return the direction of the player from the given creature (it it is not CENTER)
    fun playerDirection(x: Int, y: Int) : Char {
        return Utils.vec2dir(Vector2((player.x - x).toFloat(), (player.y - y).toFloat()))
    }

    // Returns 'n' if player is not near, else returns direction
    fun playerNearDirection(x: Int, y: Int) : Char {
        return if ((player.x - x) * (player.x - x) + (player.y - y) * (player.y - y) <= 1) {
            Utils.vec2dir(Vector2((player.x - x).toFloat(), (player.y - y).toFloat()))
        } else {
            'n'
        }
    }

    // Move the given creature on one cell to the player
    fun moveToPlayer(creature: Creature) {
        creature.move(playerDirection(creature.x, creature.y))
    }

    // Move the given creature on one cell in the direction opposite to player
    fun moveFromPlayer(creature: Creature) {
        creature.move(Utils.reverseDirection(playerDirection(creature.x, creature.y)))
    }

    // Move the given creature on one cell to be at the same line as the player
    fun moveToPlayerLine(creature: Creature) {
        val moveDir = Vector2(0f, 0f)
        when (playerDirection(creature.x, creature.y)) {
            'l', 'r' -> moveDir.y = 1f
            't', 'b' -> moveDir.x = 1f
        }
        if (creature.x > player.x) {
            moveDir.x *= -1
        } else if (creature.x == player.x) {
            moveDir.x = 0f
        }
        if (creature.y > player.y) {
            moveDir.y *= -1
        } else if (creature.y == player.y) {
            moveDir.y = 0f
        }
        creature.move(Utils.vec2dir(moveDir))
    }

    // Make the given creature attack the player if it is possible with its weapon
    fun attackPlayerIfPossible(creature: Creature, weapon: Weapon) : Boolean {
        if (weapon.area[player.x - creature.x + Const.ROOM_SIZE - 1][player.y - creature.y + Const.ROOM_SIZE - 1]) {
            weapon.cast(player.x, player.y, creature, player)
            return true
        } else {
            return false
        }
    }

    // How many cells are between the given creature and the player
    fun distToPlayer(creature: Creature) : Int {
        return abs(creature.x - player.x) + abs(creature.y - player.y)
    }
}