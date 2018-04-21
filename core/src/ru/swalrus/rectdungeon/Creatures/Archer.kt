package ru.swalrus.rectdungeon.Creatures

import ru.swalrus.rectdungeon.AI
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Room
import ru.swalrus.rectdungeon.Items.Bow
import ru.swalrus.rectdungeon.Items.Weapon
import ru.swalrus.rectdungeon.Utils

class Archer(x: Int, y: Int, room: Room) : Creature(x, y, 3, Utils.getImg("skeleton"), room) {

    val weapon: Weapon = Bow()

    override fun act() {
        if (AI.distToPlayer(this) < 3) {
            actionQueue.add { AI.moveFromPlayer(this) }
        } else {
            actionQueue.add { AI.moveToPlayerLine(this) }
        }
        actionQueue.add { if (AI.distToPlayer(this) < 3) {
            actionQueue.add { AI.moveFromPlayer(this) }
        } else {
            AI.attackPlayerIfPossible(this, weapon)
        } }
    }
}