package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Utils

class ShortSword : Weapon(Utils.getImg("short_sword"), 1) {

    override val range: Int = 1
    override val area: Char = 'l'
    override val target: Char = 'e'

    override fun getDescription(): String {
        return "A sword with a short blade." +
                "Perfect for fast and stealth killers and robbers."
    }

    override fun attack(attacker: Creature, defender: Creature) {
// TODO: Deal damage
    }
}


class Rapier : Weapon(Utils.getImg("rapier"), 2) {

    override val range: Int = 2
    override val area: Char = 'r'
    override val target: Char = 'e'

    override fun getDescription(): String {
        return "A long thin kind of sword." +
                "You can touch enemies from a long distance with this weapon."
    }

    override fun attack(attacker: Creature, defender: Creature) {

    }
}