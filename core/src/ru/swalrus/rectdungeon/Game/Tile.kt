package ru.swalrus.rectdungeon.Game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const

abstract class Tile (var img : Texture, var passable : Boolean = true) {
    // Passable - if a creature can move through the tile

    // Render the tile
    open fun draw(x : Float, y : Float, batch : SpriteBatch) {

        batch.draw(img, x, y,
                Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
    }

    // Called when a creature finish a move to the tile
    open fun onStand(creature: Creature) {

    }
}
