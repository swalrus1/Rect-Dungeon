package ru.swalrus.rectdungeon.Items

import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Utils

class HealPotion : Potion(Utils.getImg("heal_potion"), "Heal Potion") {

    override fun getDescription(): String {
        return "404\n" +
                "Not found"
    }

    override fun cast(creature: Creature) {
        creature.dealDamage(-2f)
    }
}