package ru.swalrus.rectdungeon.Creatures

import ru.swalrus.rectdungeon.AI
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Room
import ru.swalrus.rectdungeon.Items.ShortSword
import ru.swalrus.rectdungeon.Items.Weapon
import ru.swalrus.rectdungeon.Utils

class Skeleton(x: Int, y: Int, room: Room) : Creature(x, y, 3, Utils.getImg("skeleton"), room) {

    val weapon: Weapon = ShortSword()

    override fun act() {
        actionQueue.add { AI.moveToPlayer(this) }
        actionQueue.add { AI.attackPlayerIfNear(this, weapon) }
    }

    override fun onDeath() {
        dropLoot(ShortSword())
    }
}