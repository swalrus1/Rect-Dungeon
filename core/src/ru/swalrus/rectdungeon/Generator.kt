package ru.swalrus.rectdungeon

import com.badlogic.gdx.math.MathUtils
import ru.swalrus.rectdungeon.Game.*
import ru.swalrus.rectdungeon.Items.Item

class Generator {

    var progress: Int = 0
    var biome: Char = 'n'
    var lootProgress: Int = 0
    var droppedLoot: Boolean = false

    fun generate(room: Room) {

        droppedLoot = false
        lootProgress++

        val idMap = Array(Const.ROOM_SIZE,
                { _ -> Array(Const.ROOM_SIZE, { _ -> Utils.getTileID("floor") }) })

        setMap(idMap, room)

        /*for (i in 1..3) {
            Skeleton(MathUtils.random(1, Const.ROOM_SIZE), MathUtils.random(1, Const.ROOM_SIZE), room)
        }

        Chest(MathUtils.random(1, Const.ROOM_SIZE), MathUtils.random(1, Const.ROOM_SIZE), Rapier(), room)*/

        var free = Array(Const.ROOM_SIZE, { Array(Const.ROOM_SIZE, { true }) })

        for (item in chooseEnemies(progress)) {
            var x = MathUtils.random(Const.ROOM_SIZE-1)
            var y = MathUtils.random(Const.ROOM_SIZE-1)
            while (!free[x][y]) {
                x = MathUtils.random(Const.ROOM_SIZE)
                y = MathUtils.random(Const.ROOM_SIZE)
            }
            Utils.newCreature(item, biome, x+1, y+1, room)
        }
    }

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


    fun getLoot() : Item? {
        if (!droppedLoot && lootProgress >= Const.LOOT_RATE) {
            lootProgress = 0
            droppedLoot = true
            return HealPotion()
        } else {
            return null
        }
    }

    fun getCoolLoot() : Item {
        TODO()
    }

    fun chooseEnemies(progress: Int) : Array<Char> {
        return arrayOf('k', 'k', 'k')
    }
}