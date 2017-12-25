package ru.swalrus.rectdungeon

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Room {

    private var map : Array<Array<Tile>> = Array(Const.ROOM_SIZE + 2,
            { _ -> Array(Const.ROOM_SIZE + 2, { _ -> Const.FLOOR }) })


    init {
        generate()
    }


    private fun generate() {

        for (y in 1 until map.size-1) {
            map[0][y] = Const.WALL_LEFT
        }
        for (y in 1 until map.size-1) {
            map[map.size-1][y] = Const.WALL_RIGHT
        }
        for (x in 1 until map.size-1) {
            map[x][0] = Const.WALL_BOTTOM
        }
        for (x in 1 until map.size-1) {
            map[x][map.size-1] = Const.WALL_TOP
        }
    }

    fun draw(batch : SpriteBatch) {

        var xPos : Float = 0f
        var yPos : Float = 0f

        for (x in 0 until map.size)
            for (y in 0 until map.size) {
                xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
                yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
                map[x][y].draw(xPos, yPos, batch)
            }
    }
}
