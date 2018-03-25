package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.Gdx.app
import com.badlogic.gdx.math.Vector2
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

class ShortSword : Weapon(Utils.getImg("short_sword"), "Short Sword") {

    override val range: Int = 1
    override val area: Char = 'l'
    override val target: Char = 'e'
    override val requiredAP: Int = 1
    override val resetAP: Boolean = false

    override fun getDescription(): String {
        return "A sword with a short blade.\n\n" +
                "Perfect for fast and stealth killers and robbers."
    }

    override fun attack(attacker: Creature, target: Creature) {
        target.dealDamage(2f, Utils.getDirection(target.x - attacker.x, target.y - attacker.y))
    }
}


class Rapier : Weapon(Utils.getImg("rapier"), "Rapier") {

    override val range: Int = 2
    override val area: Char = 'l'
    override val target: Char = 'e'
    override val requiredAP: Int = 1
    override val resetAP: Boolean = true

    override fun getDescription(): String {
        return "A long thin kind of sword.\n\n" +
                "You can touch enemies from a long distance with this weapon."
    }

    override fun attack(attacker: Creature, target: Creature) {
        target.dealDamage(1f, Utils.getDirection(target.x - attacker.x, target.y - attacker.y))
    }
}


class Bow : Weapon(Utils.getImg("bow"), "Bow") {

    override val area: Char = 'l'
    override val range: Int = 9
    override val target: Char = 'e'
    override val requiredAP: Int = 1
    override val resetAP: Boolean = true

    override fun getDescription(): String {
        return "A long bow made with an expensive sort of wood.\n\n" +
                "It's quite heavy."
    }

    override fun attack(attacker: Creature, target: Creature) {
        val dir = Utils.vec2dir(target.x - attacker.x, target.y - attacker.y)
        val arrow = Arrow(dir, { creature, caster, dir -> arrowLand(creature, caster, dir) })
        if (attacker is Player) {
            attacker.AP++
        }
        attacker.throwItem(arrow, target.x, target.y)
    }

    private fun arrowLand(creature: Creature?, caster: Creature, dir: Char) {
        if (creature != null) {
            creature.dealDamage(2f, dir)
        } else {
            app.error("Arrow", "arrow target is null")
        }
    }
}