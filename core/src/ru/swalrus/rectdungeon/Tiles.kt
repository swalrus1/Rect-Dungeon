package ru.swalrus.rectdungeon

import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Tile

class Wall(direction: Int) : Tile(Const.getWallImg(direction), passable = false)

class Floor : Tile(Const.images["FLOOR"]!!)