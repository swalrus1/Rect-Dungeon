package ru.swalrus.rectdungeon

import ru.swalrus.rectdungeon.Game.Creature

class HealPotion() : Potion(Utils.getImg("heal_potion"), "Heal Potion") {

    override fun getDescription(): String {
        return "404\n" +
                "Not found"
    }

    override fun cast(creature: Creature) {
        creature.dealDamage(-2f)
    }
}