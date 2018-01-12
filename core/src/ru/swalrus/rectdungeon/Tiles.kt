package ru.swalrus.rectdungeon

class Wall(direction: Int) : Tile(Const.getWallImg(direction), passable = false)

class Floor : Tile(Const.images["FLOOR"]!!)


class Door(direction: Int, room: Room) : Tile(Const.getDoorImg(direction), passable = false) {
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
            creature.move(direction)
        }
    }
}