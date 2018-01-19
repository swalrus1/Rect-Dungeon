package ru.swalrus.rectdungeon

class Chunk {

    // Direction - направление СОХРАНЕННОЙ комнаты
    var direction : Int = Const.CENTER
    var Last : Room
    var Center : Room


    init {
        Center = Room(this)
        Last = Room(this)
    }


    fun move(dir: Int) {

        if (dir == direction) {
            var temp = Center
            Center = Last
            Last = temp
            direction = Utils.reverseDirection(dir)
        } else {
            Last = Center
            Center = Room(this)
            direction = Utils.reverseDirection(dir)
        }
    }
}
