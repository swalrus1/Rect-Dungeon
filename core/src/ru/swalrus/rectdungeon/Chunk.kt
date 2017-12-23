package ru.swalrus.rectdungeon

class Chunk internal constructor() {

    var Left: Room
    var Right: Room
    var Top: Room
    var Bottom: Room
    var Center: Room


    init {
        Center = Room()
        Top = Room()
        Bottom = Room()
        Left = Room()
        Right = Room()
    }


    fun move(direction: Int) {

        when (direction) {

            Const.LEFT -> {
                Right = Center
                Center = Left
                Left = Room()
                Top = Room()
                Bottom = Room()
            }

            Const.RIGHT -> {
                Left = Center
                Center = Right
                Right = Room()
                Top = Room()
                Bottom = Room()
            }

            Const.BOTTOM -> {
                Top = Center
                Center = Bottom
                Bottom = Room()
                Left = Room()
                Right = Room()
            }

            Const.TOP -> {
                Bottom = Center
                Center = Top
                Top = Room()
                Left = Room()
                Right = Room()
            }
        }
    }
}
