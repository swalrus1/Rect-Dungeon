package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Utils

class ShortSword : Weapon(Utils.getImg("short_sword"), 1) {

    override fun getDescription(): String {
        return "A sword with a short blade." +
                "Perfect for fast and stealth killers and robbers."
    }

    override fun draw(batch: SpriteBatch, x: Float, y: Float, scale: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}