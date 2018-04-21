package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Area
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

abstract class RangedWeapon (img: Texture, name: String) : Weapon(img, name) {

    override val area: Array<Array<Boolean>> = Area.line(Const.ROOM_SIZE - 1)
    override val target: Char = 'e'

    // Called when an arrow is landed
    abstract fun arrowEffect(target: Creature?, caster: Creature, dir: Char)

    override fun attack(attacker: Creature, target: Creature) {
        val dir = Utils.vec2dir(target.x - attacker.x, target.y - attacker.y)
        val arrow = Arrow(dir, { target, caster, dir -> arrowEffect(target, caster, dir) })
        // It is needed to throw an arrow
        if (attacker is Player) {
            attacker.AP++
        }
        attacker.throwItem(arrow, target.x, target.y)
    }

    override fun cast(x: Int, y: Int, attacker: Creature, defender: Creature?) {
        attacker.actionQueue.add({
            attacker.attack(Const.CENTER, defender!!, {a, b -> attack(a, b)}, requiredAP, resetAP)
        })
    }
}