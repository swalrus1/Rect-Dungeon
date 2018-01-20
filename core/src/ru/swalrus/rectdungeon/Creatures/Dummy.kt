package ru.swalrus.rectdungeon.Creatures

import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Room
import ru.swalrus.rectdungeon.Utils

class Dummy (x: Int, y: Int, room: Room) : Creature(x, y, 3, Utils.getImg("human"), room) {

    override fun makeTurn() {
        move(Utils.randomDirection())
    }
}