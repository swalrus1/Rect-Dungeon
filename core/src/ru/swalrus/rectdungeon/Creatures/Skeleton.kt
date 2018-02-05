package ru.swalrus.rectdungeon.Creatures

import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Game.Room
import ru.swalrus.rectdungeon.Items.ShortSword
import ru.swalrus.rectdungeon.Items.Weapon
import ru.swalrus.rectdungeon.Utils

class Skeleton(x: Int, y: Int, room: Room) : Creature(x, y, 3, Utils.getImg("skeleton"), room) {

    val weapon: Weapon = ShortSword()

    override fun act() {
        if (room.getCreatureAt(x+1, y) is Player) {
            weapon.cast(x+1, y, this, room.getCreatureAt(x+1, y))
        } else if (room.getCreatureAt(x-1, y) is Player) {
            weapon.cast(x-1, y, this, room.getCreatureAt(x-1, y))
        } else if (room.getCreatureAt(x, y+1) is Player) {
            weapon.cast(x, y+1, this, room.getCreatureAt(x, y+1))
        } else if (room.getCreatureAt(x, y-1) is Player) {
            weapon.cast(x, y-1, this, room.getCreatureAt(x, y-1))
        } else {
            move(Utils.randomDirection())
        }
    }
}