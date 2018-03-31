package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

abstract class RangedWeapon (img: Texture, name: String) : Weapon(img, name) {

    override val area: Char = 'l'
    override val range: Int = 9
    override val target: Char = 'e'

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
}