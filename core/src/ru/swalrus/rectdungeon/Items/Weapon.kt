package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

abstract class Weapon(img: Texture, id: Int, name: String) : Item(img, id, name), Castable {

    var equipped: Boolean = false

    override var actionName: String = "Equip"
    // TODO equip/unequip

    override fun cast(x: Int, y: Int, attacker: Creature, defender: Creature?) {
        val direction: Char = if (area == 'l')
            Utils.getDirection(x - attacker.x, y - attacker.y)
        else
            Const.CENTER
        attacker.attack(direction, defender!!, {a, b -> attack(a, b)}, requiredAP, resetAP)
    }

    override fun use(player: Player) {
        if (equipped) {

        } else {
            player.equip(this)
            equipped = true
        }
    }

    abstract fun attack(attacker: Creature, target: Creature)
}