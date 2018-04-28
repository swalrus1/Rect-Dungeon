package ru.swalrus.rectdungeon.Game

import com.badlogic.gdx.Gdx.app
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Generator
import ru.swalrus.rectdungeon.Utils
import kotlin.math.abs

class Room (val chunk: Chunk, val generator: Generator) {

    var map : Array<Array<Tile>> = Array<Array<Tile>>(Const.ROOM_SIZE + 2,  // Tiles of the room, including walls
            { _ -> Array(Const.ROOM_SIZE + 2, { _ -> Floor() }) })
    private var creatureList : MutableList<Creature> = mutableListOf()          // All creatures contained in the room
    private var currentCreature : Int = 0                                       // Index of the making move creature
    private var removeQueue: MutableList<Pair<Int, Int>> =                      // All creatures which are being removed
            MutableList(0, { _ -> Pair(0, 0) })

    var yellowArea: Array<Array<Boolean>> = Array(Const.ROOM_SIZE,              // What cells can be casted (tapped) to
            { _ -> Array(Const.ROOM_SIZE, { _ -> false }) })
    var grayArea: Array<Array<Boolean>> = Array(Const.ROOM_SIZE,                // Range of the weapon
            { _ -> Array(Const.ROOM_SIZE, { _ -> false }) })
    var cleared: Boolean = false


    init {
        generator.generate(this)
        closeDoors()
    }


    // Prohibit getting out of the room
    fun closeDoors() {
        for (row in map)
            for (tile in row) {
                if (tile is Door) {
                    tile.close()
                }
            }
    }

    // Called when the room is cleared
    fun clearRoom() {
        for (row in map)
            for (tile in row) {
                if (tile is Door) {
                    tile.open()
                }
            }
        generator.progress++
        cleared = true
    }

    // x, y - coordinates of the caster
    // area - type of area (Self, Line, Range)
    // target - type of target (All, Creatures, Player, Enemies)
    // range - parameter of the area
    fun setYellowArea(centerX: Int, centerY: Int, area: Array<Array<Boolean>>, target: Char) {
        resetYellowArea()
        for (x in 0 until Const.ROOM_SIZE)
            for (y in 0 until Const.ROOM_SIZE) {
                if (area[x - centerX + Const.ROOM_SIZE - 1][y - centerY + Const.ROOM_SIZE - 1]) {
                    grayArea[x][y] = true
                    if (Utils.isTarget(getCreatureAt(x+1, y+1), target)) {
                        yellowArea[x][y] = true
                    }
                }
            }
    }

    // Set yellow area to null
    fun resetYellowArea() {
        yellowArea = Array(Const.ROOM_SIZE, { _ -> Array(Const.ROOM_SIZE, { _ -> false }) })
        grayArea = Array(Const.ROOM_SIZE, { _ -> Array(Const.ROOM_SIZE, { _ -> false }) })
    }

    // Add a new creature to the room (to the creatureList)
    fun addCreature(creature : Creature) {
        creatureList.add(creature)
    }

    // Make player to make turn if it is in the room (player makes turn in the next render cycle)
    fun setFocusToPlayer() {
        var i = 0
        while ((i < creatureList.size) && (creatureList[i] !is Player)) {
            i++
        }
        if (i < creatureList.size) {
            currentCreature = i
        }
    }

    // Returns player if it is in the room
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

    // Returns a creature from (x, y) if there is one
    fun getCreatureAt(x: Int, y: Int) : Creature? {
        for (item in creatureList) {
            if ((item.x == x) and (item.y == y)) {
                return item
            }
        }
        return null
    }

    // Removes a creature from (x, y) if there is one
    fun removeCreatureAt(x: Int, y: Int) {
        removeQueue.add(Pair(x, y))
    }

    // The main render fun
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

        // Render tiles
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

        // Render creatures
        for (creature in creatureList) {
            creature.render(batch)
        }

        // Check if the room is cleared
        if (!cleared && creatureList.size <= 1 && findPlayer() != null) {
            clearRoom()
        }
    }

    // Returns a tile from (x, y)
    fun getTile(x: Int, y: Int) : Tile {
        return try {
            map[x][y]
        } catch (e: IndexOutOfBoundsException) {
            EmptyTile()
        }
    }
}
