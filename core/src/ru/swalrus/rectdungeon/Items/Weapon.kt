package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Utils

abstract class Weapon(img: Texture, id: Int) : Item(img, id), Castable {

    override fun cast(x: Int, y: Int, attacker: Creature, defender: Creature?) {
        attacker.attack(Utils.getDirection(x - attacker.x, y - attacker.y), defender!!, {a, b -> attack(a, b)})
        // Make attacker to move to the target, then deal it damage
    }

    abstract fun attack(attacker: Creature, defender: Creature)
    // TODO: Сделать возможность не двигаться в сторону врага
}