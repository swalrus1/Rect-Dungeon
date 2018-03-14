package ru.swalrus.rectdungeon.Game

import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Generator
import ru.swalrus.rectdungeon.Utils

class Chunk (val generator: Generator) {

    // Direction - направление СОХРАНЕННОЙ комнаты
    private var direction : Char = Const.CENTER
    var Last : Room
    var Center : Room


    init {
        Center = Room(this, generator)
        Last = Room(this, generator)
    }


    fun move(dir: Char) {

        if (dir == direction) {
            val temp = Center
            Center = Last
            Last = temp
            direction = Utils.reverseDirection(dir)
        } else {
            Last = Center
            Center = Room(this, generator)
            direction = Utils.reverseDirection(dir)
        }
    }
}
