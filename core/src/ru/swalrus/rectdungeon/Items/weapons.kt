package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.Gdx.app
import ru.swalrus.rectdungeon.Area
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

class ShortSword : Weapon(Utils.getImg("short_sword"), "Short Sword") {

    override val area: Array<Array<Boolean>> = Area.line(1)
    override val target: Char = 'e'
    override val requiredAP: Int = 1
    override val resetAP: Boolean = false

    override fun getDescription(): String {
        return "A sword with a short blade.\n\n" +
                "Perfect for fast and stealth killers and robbers."
    }

    override fun attack(attacker: Creature, target: Creature) {
        target.dealDamage(2f, attacker)
    }
}


class LongSword : Weapon(Utils.getImg("long_sword"), "Long Sword") {

    override val area: Array<Array<Boolean>> = Area.line(1)
    override val target: Char = 'e'
    override val requiredAP: Int = 1
    override val resetAP: Boolean = true

    override fun getDescription(): String {
        return "A long powerful sword with a steel blade.\n\n" +
                "This heavy sword is used by skilled guardians and troops."
    }

    override fun attack(attacker: Creature, target: Creature) {
        target.dealDamage(3f, attacker)
    }
}


class Rapier : Weapon(Utils.getImg("rapier"), "Rapier") {

    override val area: Array<Array<Boolean>> = Area.line(2)
    override val target: Char = 'e'
    override val requiredAP: Int = 1
    override val resetAP: Boolean = true

    override fun getDescription(): String {
        return "A long thin kind of sword.\n\n" +
                "You can touch enemies from a long distance with this weapon."
    }

    override fun attack(attacker: Creature, target: Creature) {
        target.dealDamage(2f, attacker)
    }
}


class Hammer : Weapon(Utils.getImg("hammer"), "Hammer") {

    override val area: Array<Array<Boolean>> = Area.line(1)
    override val target: Char = 'e'
    override val requiredAP: Int = 2
    override val resetAP: Boolean = false

    override fun getDescription(): String {
        return "This giant rock is usually used to nail," +
                "but it can be very useful to smash somebody too."
    }

    override fun attack(attacker: Creature, target: Creature) {
        target.dealDamage(5f, attacker)
    }
}


class Dagger : Weapon(Utils.getImg("dagger"), "Dagger") {

    override val area: Array<Array<Boolean>> = Area.line(1)
    override val target: Char = 'e'
    override val requiredAP: Int = 1
    override val resetAP: Boolean = false

    override fun attack(attacker: Creature, target: Creature) {
        target.dealDamage(1f, attacker)
    }

    override fun getDescription(): String {
        return "A strong knife with a sharp blade.\n\n" +
                "It can be found everywhere."
    }
}


class LongBow : RangedWeapon(Utils.getImg("long_bow"), "Long Bow") {

    override val requiredAP: Int = 2
    override val resetAP: Boolean = false

    override fun getDescription(): String {
        return "A long bow made with an expensive sort of wood.\n\n" +
                "It's quite heavy."
    }

    override fun arrowEffect(target: Creature?, caster: Creature, dir: Char) {
        if (target != null) {
            target.dealDamage(2f, dir)
        } else {
            app.error("Arrow", "arrow target is null")
        }
    }
}


class ShortBow : RangedWeapon(Utils.getImg("bow"), "Short Bow") {

    override val requiredAP: Int = 1
    override val resetAP: Boolean = false

    override fun getDescription(): String {
        return "A short bow usually used by hunters."
    }

    override fun arrowEffect(target: Creature?, caster: Creature, dir: Char) {
        if (target != null) {
            target.dealDamage(1f, dir)
        } else {
            app.error("Arrow", "arrow target is null")
        }
    }
}