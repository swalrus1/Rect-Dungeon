package ru.swalrus.rectdungeon

class Room {

    private var map : Array<Array<Tile>> = arrayOf(arrayOf(Const.FLOOR))


    init {
        //generate()
    }


    private fun generate() {

        for (x in 0 .. Const.ROOM_SIZE)
            for (y in 0 .. Const.ROOM_SIZE) {

                map[x][y] = Const.FLOOR
            }
    }

    fun draw() {

        for (x in 0 .. Const.ROOM_SIZE)
            for (y in 0 .. Const.ROOM_SIZE) {
                map[x][y].draw(x * Const.TILE_SIZE, y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM)
                // TODO: Проверить, все ли работает
            }
    }
}
