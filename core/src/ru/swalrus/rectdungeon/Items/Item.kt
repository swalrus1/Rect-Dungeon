package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class Item (var img: Texture, val id: Int) {

    abstract fun getDescription() : String

    abstract fun draw(batch: SpriteBatch, x: Float, y: Float, scale: Float = 1f)
}