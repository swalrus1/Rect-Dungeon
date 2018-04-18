package ru.swalrus.rectdungeon

import com.badlogic.gdx.math.MathUtils
import ru.swalrus.rectdungeon.Game.*
import ru.swalrus.rectdungeon.Items.HealPotion
import ru.swalrus.rectdungeon.Items.Item

// The class that is responsible for creating new rooms
class Generator {

    var progress: Int = 0                       // How long has the player moved through a biome
    var biome: Char = 'n'                       // The current level of the dungeon
    var lootProgress: Int = 0                   // How many turns the player has the player made without getting loot
    var droppedLoot: Boolean = false            // Has loot been dropped in the current turn

    fun generate(room: Room) {

        droppedLoot = false
        lootProgress++

        val idMap = Array(Const.ROOM_SIZE,
                { _ -> Array(Const.ROOM_SIZE, { _ -> Utils.getTileID("floor") }) })

        setMap(idMap, room)

        // Represents free cells in the room
        val free = Array(Const.ROOM_SIZE, { Array(Const.ROOM_SIZE, { true }) })

        // For each enemy choose a free cell,
        // then place it to this cell and make the cell not free
        for (item in chooseEnemies(progress)) {
            var x = MathUtils.random(Const.ROOM_SIZE-1)
            var y = MathUtils.random(Const.ROOM_SIZE-1)
            while (!free[x][y]) {
                x = MathUtils.random(Const.ROOM_SIZE-1)
                y = MathUtils.random(Const.ROOM_SIZE-1)
            }
            Utils.newCreature(item, biome, x+1, y+1, room)
            free[x][y] = false
        }
    }

    // Set the given tiles to the room
    // arr - array of ID of tiles
    // doors - represents existing of doors
    fun setMap(arr: Array<Array<Int>>, room: Room, doors: Array<Boolean> = arrayOf(true, true, true, true)) {
        if ((arr.size == Const.ROOM_SIZE) and (arr[0].size == Const.ROOM_SIZE)) {
            val size = room.map.size
            for (y in 1 until size) {
                room.map[0][y] = Wall(Const.LEFT)
            }
            for (y in 1 until size) {
                room.map[size-1][y] = Wall(Const.RIGHT)
            }
            for (x in 1 until size-1) {
                room.map[x][0] = Wall(Const.BOTTOM)
            }
            for (x in 1 until size-1) {
                room.map[x][size-1] = Wall(Const.TOP)
            }
            if (doors[0]) room.map[size / 2][size - 1] = Door(Const.TOP, room)
            if (doors[1]) room.map[size / 2][0] = Door(Const.BOTTOM, room)
            if (doors[2]) room.map[0][size / 2] = Door(Const.LEFT, room)
            if (doors[3]) room.map[size - 1][size / 2] = Door(Const.RIGHT, room)

            for (x in 0 until Const.ROOM_SIZE)
                for (y in 0 until Const.ROOM_SIZE) {
                    val tile = Utils.getTile(arr[x][y])
                    room.map[x+1][y+1] = when (tile) {
                        is Lava -> Lava(Utils.getLavaImg(arr, x, y, { id: Int -> Utils.getTile(id) is Lava }))
                        else -> tile
                    }
                }
        }
    }


    // Returns a random loot if it must be dropped,
    // in other case returns 'null'
    fun getLoot() : Item? {
        if (!droppedLoot && lootProgress >= Const.LOOT_RATE) {
            lootProgress = 0
            droppedLoot = true
            return HealPotion()
        } else {
            return null
        }
    }

    // Returns a random loot that is better than usual loot if it must be dropped,
    // in other case returns 'null'
    fun getCoolLoot() : Item {
        TODO()
    }

    // Returns an array of enemy IDs which must be placed to the current room
    fun chooseEnemies(progress: Int) : Array<Char> {
        return arrayOf('k', 'k', 'k')
    }
}