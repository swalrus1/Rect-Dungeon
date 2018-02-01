package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Utils

class ShortSword : Weapon(Utils.getImg("short_sword"), 1) {

    override val range: Int = 1
    override val area: Char = 'l'
    override val target: Char = 'e'

    override fun getDescription(): String {
        return "A sword with a short blade." +
                "Perfect for fast and stealth killers and robbers."
    }
}