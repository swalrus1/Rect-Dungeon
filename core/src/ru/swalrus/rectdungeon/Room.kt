package ru.swalrus.rectdungeon

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Room {

    private var map : Array<Array<Tile>> = Array(Const.ROOM_SIZE,
            { _ -> Array(Const.ROOM_SIZE, { _ -> Const.FLOOR }) })


    init {
        //generate()
    }


    private fun generate() {

        for (x in 0 until Const.ROOM_SIZE)
            for (y in 0 until Const.ROOM_SIZE) {

                map[x][y] = Const.FLOOR
            }
    }

    fun draw(batch : SpriteBatch) {

        for (x in 0 until Const.ROOM_SIZE)
            for (y in 0 until Const.ROOM_SIZE) {
                map[x][y].draw(x * Const.TILE_SIZE, y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM, batch)
                // TODO: Проверить, все ли работает
            }
    }
}
