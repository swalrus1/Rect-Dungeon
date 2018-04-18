package ru.swalrus.rectdungeon

import kotlin.math.abs

// An object to store area presets
object Area {

    val len: Int = Const.ROOM_SIZE * 2 - 1

    // Returns an area of two lines: horizontal and vertical
    fun line(range: Int) : Array<Array<Boolean>> {
        val area = Array(Const.ROOM_SIZE * 2 - 1,
                { _ -> Array(Const.ROOM_SIZE * 2 - 1, { _ -> false }) })
        for (i in (Const.ROOM_SIZE - 1 - range)..(Const.ROOM_SIZE - 1 + range)) {
            area[Const.ROOM_SIZE - 1][i] = true
            area[i][Const.ROOM_SIZE - 1] = true
        }
        return area
    }

    // Returns an area...
    fun range(range: Int) : Array<Array<Boolean>> {
        val area = Array(Const.ROOM_SIZE * 2 - 1,
                { x -> Array(Const.ROOM_SIZE * 2 - 1, { y -> (
                        abs(x - Const.ROOM_SIZE - 1) + abs(y - Const.ROOM_SIZE - 1) <= range) }) })
        return area
    }
}