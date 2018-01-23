package ru.swalrus.rectdungeon.Game

import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Utils

class Wall(direction: Int) : Tile(Utils.getImg("wall", direction), passable = false)

class Floor : Tile(Utils.getImg("floor"))

class EmptyTile : Tile(Utils.getImg("empty"), passable = false)


class Door(direction: Int, room: Room) : Tile(Utils.getImg("door", direction), passable = false) {
    var direction: Int = direction
    var room: Room = room

    override fun onStand(creature: Creature) {
        if (creature is Player) {
            room.chunk.move(direction)
            room.removeCreatureAt(creature.x, creature.y)
            creature.room = room.chunk.Center
            room.chunk.Center.addCreature(creature)
            when (direction) {
                Const.TOP -> {
                    creature.x = Const.ROOM_SIZE / 2 + 1
                    creature.y = 0
                }
                Const.BOTTOM -> {
                    creature.x = Const.ROOM_SIZE / 2 + 1
                    creature.y = Const.ROOM_SIZE + 1
                }
                Const.LEFT -> {
                    creature.x = Const.ROOM_SIZE + 1
                    creature.y = Const.ROOM_SIZE / 2 + 1
                }
                Const.RIGHT -> {
                    creature.x = 0
                    creature.y = Const.ROOM_SIZE / 2 + 1
                }
            }
            creature.move(direction, true)
            creature.endTurn()
            // TODO: Закрываем двери
            creature.act()
        }
    }
}