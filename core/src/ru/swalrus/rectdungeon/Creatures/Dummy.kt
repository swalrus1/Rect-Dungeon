package ru.swalrus.rectdungeon.Creatures

import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Creature
import ru.swalrus.rectdungeon.Room

class Dummy (x: Int, y: Int, room: Room) : Creature(x, y, 3, Const.images["HUMAN"]!!, room) {

    override fun makeTurn() {
        move(Const.randomDirection())
    }
}