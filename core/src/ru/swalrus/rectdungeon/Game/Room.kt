package ru.swalrus.rectdungeon.Game

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Generator
import ru.swalrus.rectdungeon.Utils
import kotlin.math.abs

class Room (val chunk: Chunk, generator: Generator) {

    // Tiles
    var map : Array<Array<Tile>> = Array<Array<Tile>>(Const.ROOM_SIZE + 2,
            { _ -> Array(Const.ROOM_SIZE + 2, { _ -> Floor() }) })
    // Creatures
    private var creatureList : MutableList<Creature> = mutableListOf()
    private var currentCreature : Int = 0
    private var removeQueue: MutableList<Pair<Int, Int>> = MutableList(0, { _ -> Pair(0, 0) })

    var yellowArea: Array<Array<Boolean>> = Array(Const.ROOM_SIZE,
            { _ -> Array(Const.ROOM_SIZE, { _ -> false }) })
    var grayArea: Array<Array<Boolean>> = Array(Const.ROOM_SIZE,
            { _ -> Array(Const.ROOM_SIZE, { _ -> false }) })


    init {
        generator.generate(this)
    }


    fun setYellowArea(x: Int, y: Int, area: Char, target: Char, range: Int = 1) {
        resetYellowArea()
        when (area) {
            's' -> yellowArea[x][y] = true
            'l' -> {
                for (i in x-range..x+range) {
                    if ((i >= 0) and (i < Const.ROOM_SIZE)) {
                        grayArea[i][y] = true
                        if (Utils.isTarget(getCreatureAt(i+1, y+1), target)) {
                            yellowArea[i][y] = true
                        }
                    }
                }
                for (i in y-range..y+range) {
                    if ((i >= 0) and (i < Const.ROOM_SIZE)) {
                        grayArea[x][i] = true
                        if (Utils.isTarget(getCreatureAt(x+1, i+1), target)) {
                            yellowArea[x][i] = true
                        }
                    }
                }
                yellowArea[x][y] = false
                grayArea[x][y] = false
            }
            'r' -> {
                for (nx in 0 until Const.ROOM_SIZE)
                    for (ny in 0 until Const.ROOM_SIZE) {
                        if ((abs(nx - x) + abs(ny - y) <= range)) {
                            grayArea[nx][ny] = true
                            if (Utils.isTarget(getCreatureAt(nx + 1, ny + 1), target)) {
                                yellowArea[nx][ny] = true
                            }
                        }
                    }
                yellowArea[x][y] = false
                grayArea[x][y] = false
            }
        }
    }

    fun resetYellowArea() {
        yellowArea = Array(Const.ROOM_SIZE, { _ -> Array(Const.ROOM_SIZE, { _ -> false }) })
        grayArea = Array(Const.ROOM_SIZE, { _ -> Array(Const.ROOM_SIZE, { _ -> false }) })
    }

    fun addCreature(creature : Creature) {
        creatureList.add(creature)
    }

    fun setFocusToPlayer() {
        var i = 0
        while ((i < creatureList.size) && (creatureList[i] !is Player)) {
            i++
        }
        if (i < creatureList.size) {
            currentCreature = i
        }
    }

    fun findPlayer() : Player? {
        var i = 0
        while ((i < creatureList.size) && (creatureList[i] !is Player)) {
            i++
        }
        if (i < creatureList.size) {
            val temp = creatureList[i]
            if (temp is Player) {
                return temp
            } else {
                return null
            }
        } else {
            return null
        }
    }

    fun getCreatureAt(x: Int, y: Int) : Creature? {
        for (item in creatureList) {
            if ((item.x == x) and (item.y == y)) {
                return item
            }
        }
        return null
    }

    fun removeCreatureAt(x: Int, y: Int) {
        removeQueue.add(Pair(x, y))
    }

    fun render(batch : SpriteBatch) {

        // If the current creature is ready to end turn,
        if (currentCreature < creatureList.size && (creatureList[currentCreature].ready || !creatureList[currentCreature].alive)) {
            // Move focus to the next creature
            if (currentCreature >= creatureList.size - 1) {
                currentCreature = 0
            } else {
                currentCreature++
            }
            // If it isn't sleeping AND is not in remove queue
            if (creatureList[currentCreature].isActive() and
                    (Pair(creatureList[currentCreature].x, creatureList[currentCreature].y) !in removeQueue)) {
                // Say him to make turn
                creatureList[currentCreature].startTurn()
            }
        }

        // Remove creatures from remove queue
        for (pos in removeQueue) {
            for (creature in creatureList) {
                if ((creature.x == pos.first) and (creature.y == pos.second)) {
                    val i = creatureList.indexOf(creature)
                    creatureList.removeAt(i)
                    if (currentCreature > i) {
                        currentCreature--
                    }
                    break
                }
            }
        }
        removeQueue.clear()

        var xPos : Float
        var yPos : Float

        for (x in 0 until map.size)
            for (y in map.size-1 downTo 0) {
                xPos = x * Const.TILE_SIZE + Const.MAP_MARGIN_LEFT
                yPos = y * Const.TILE_SIZE + Const.MAP_MARGIN_BOTTOM
                map[x][y].draw(xPos, yPos, batch)
                if ((x-1 in 0 until yellowArea.size) and (y-1 in 0 until yellowArea.size)) {
                    if (yellowArea[x-1][y-1]) {
                        batch.draw(Utils.getImg("yellow_area"), xPos, yPos,
                                Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
                    } else if (grayArea[x-1][y-1]) {
                        batch.draw(Utils.getImg("gray_area"), xPos, yPos,
                                Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
                    }
                }
            }

        for (creature in creatureList) {
            creature.render(batch)
        }
    }

    fun getTile(x: Int, y: Int) : Tile {
        return try {
            map[x][y]
        } catch (e: IndexOutOfBoundsException) {
            EmptyTile()
        }
    }
}
