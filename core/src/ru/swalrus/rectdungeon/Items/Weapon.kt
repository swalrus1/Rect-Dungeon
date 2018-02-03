package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Utils

abstract class Weapon(img: Texture, id: Int) : Item(img, id), Castable {

    override fun cast(x: Int, y: Int, attacker: Creature, defender: Creature?) {
        val direction: Int = if (area == 'l')
            Utils.getDirection(x - attacker.x, y - attacker.y)
        else
            Const.CENTER
        attacker.attack(direction, defender!!, {a, b -> attack(a, b)})
    }

    abstract fun attack(attacker: Creature, target: Creature)
}