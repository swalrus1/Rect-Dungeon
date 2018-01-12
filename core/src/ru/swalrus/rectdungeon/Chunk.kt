package ru.swalrus.rectdungeon

class Chunk internal constructor() {

    // Direction - направление СОХРАНЕННОЙ комнаты
    var direction : Int = Const.CENTER
    var Last : Room
    var Center : Room


    init {
        Center = Room(this)
        Last = Room(this)
    }


    fun backDirection (direction : Int) : Int {

        when (direction) {
            Const.TOP -> return Const.BOTTOM
            Const.BOTTOM -> return Const.TOP
            Const.RIGHT -> return Const.LEFT
            Const.LEFT -> return Const.RIGHT
            Const.CENTER -> return Const.CENTER
            else -> return Const.CENTER
        }
    }

    fun move(dir: Int) {

        if (dir == direction) {
            var temp = Center
            Center = Last
            Last = temp
            direction = backDirection(dir)
        } else {
            Last = Center
            Center = Room(this)
            direction = backDirection(dir)
        }
    }
}
