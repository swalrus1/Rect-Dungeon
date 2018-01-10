package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Room {

    private var creatureList : MutableList<Creature> = mutableListOf()
    private var map : Array<Array<Tile>> = Array(Const.ROOM_SIZE + 2,
            { _ -> Array(Const.ROOM_SIZE + 2, { _ -> Const.emptyTile })})
    private var currentCreature : Int = 0


    init {
        generate()
    }


    fun addCreature(creature : Creature) {
        creatureList.add(creature)
    }

    fun getCreature(x: Int, y: Int) : Creature? {
        for (item in creatureList) {
            if ((item.x == x) and (item.y == y)) {
                return item
            }
        }
        return null
    }

    private fun generate() {

        for (x in 1 until map.size-1)
            for (y in 1 until map.size-1) {
                map[x][y] = Floor()
            }

        for (y in 1 until map.size-1) {
            map[0][y] = Wall(Const.LEFT)
        }
        for (y in 1 until map.size-1) {
            map[map.size-1][y] = Wall(Const.RIGHT)
        }
        for (x in 1 until map.size-1) {
            map[x][0] = Wall(Const.BOTTOM)
        }
        for (x in 1 until map.size-1) {
            map[x][map.size-1] = Wall(Const.TOP)
        }
    }

    fun draw(batch : SpriteBatch) {

        if (creatureList[currentCreature].ready) {
            // Move focus to the next creature
            if (currentCreature >= creatureList.size - 1) {
                currentCreature = 0
            } else {
                currentCreature++
            }
            // If it isn't sleeping
            if (creatureList[currentCreature].isActive()) {
                // Say him to make turn
                creatureList[currentCreature].makeTurn()
            }
        }
        /* TODO: При смерти существо становится неактивным, добавляется в очередь на удаление,
           TODO: и после того, как каждый сделал ход, удаляется из списка, затем очередь очищается. */

        var xPos : Float = 0f
        var yPos : Float = 0f

        for (x in 0 until map.size)
            for (y in map.size-1 downTo 0) {
                xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
                yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
                map[x][y].draw(xPos, yPos, batch)
            }

        for (creature in creatureList) {
            creature.render(batch)
        }
    }

    fun getTile(x: Int, y: Int) : Tile {
        return map[x][y]
    }
}
