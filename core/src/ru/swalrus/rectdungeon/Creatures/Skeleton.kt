package ru.swalrus.rectdungeon.Creatures

import com.badlogic.gdx.Gdx.app
import ru.swalrus.rectdungeon.AI
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Game.Room
import ru.swalrus.rectdungeon.Items.ShortSword
import ru.swalrus.rectdungeon.Items.Weapon
import ru.swalrus.rectdungeon.Utils
import kotlin.math.roundToInt

class Skeleton(x: Int, y: Int, room: Room) : Creature(x, y, 3, Utils.getImg("skeleton"), room) {

    val weapon: Weapon = ShortSword()

    override fun act() {
        val dir = AI.playerNearDirection(x, y)
        if (dir != 'n') {
            val t = Utils.dir2vec(dir)
            val nx = x + t.x.roundToInt()
            val ny = y + t.y.roundToInt()
            app.log("debug", "$nx $ny")
            weapon.cast(nx, ny, this, room.getCreatureAt(nx, ny))
        } else {
            move(AI.playerDirection(x, y))
        }
    }

    override fun onDeath() {
        dropLoot(ShortSword())
    }
}