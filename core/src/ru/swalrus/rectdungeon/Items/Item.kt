package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player

abstract class Item (var img: Texture, val name: String, val bigImage: Texture = img) {

    abstract fun getDescription() : String
    abstract fun land(creature: Creature?, caster: Creature)
    abstract fun use(player: Player)
    abstract var actionName: String

    var new: Boolean = true

    fun draw(batch: SpriteBatch, x: Float, y: Float) {
        batch.draw(img, x, y, Const.TILE_SIZE, Const.TILE_SIZE, 0f, 1f, 1f, 0f)
    }
}