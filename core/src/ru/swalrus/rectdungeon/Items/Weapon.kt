package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.Gdx.app
import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

abstract class Weapon(img: Texture, name: String) : Item(img, name), Castable {

    var equipped: Boolean = false

    override var actionName: String = "Equip"
        get() {
            return if (equipped) {
                "Unequip"
            } else {
                "Equip"
            }
        }

    override fun cast(x: Int, y: Int, attacker: Creature, defender: Creature?) {
        val direction: Char = if (area == 'l')
            Utils.getDirection(x - attacker.x, y - attacker.y)
        else
            Const.CENTER
        attacker.attack(direction, defender!!, {a, b -> attack(a, b)}, requiredAP, resetAP)
    }

    override fun use(player: Player) {
        if (equipped) {
            player.unequip(this)
            equipped = false
        } else {
            player.equip(this)
            equipped = true
            app.log("debug", "weapon -> equipped")
        }
    }

    override fun land(creature: Creature?, caster: Creature) {
        // TODO edit
        if (creature != null) {
            creature.dealDamage(2f)
        }
    }

    abstract fun attack(attacker: Creature, target: Creature)
}