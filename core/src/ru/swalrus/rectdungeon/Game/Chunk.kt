package ru.swalrus.rectdungeon.Game

import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Generator
import ru.swalrus.rectdungeon.Utils

class Chunk (val generator: Generator) {

    private var direction : Char = Const.CENTER             // direction of the SAVED room
    var Last : Room
    var Center : Room


    init {
        Center = Room(this, generator)
        Last = Room(this, generator)
    }


    // Move chunk to the given direction
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
