package ru.swalrus.rectdungeon.Items

import ru.swalrus.rectdungeon.Utils

class Rapier : Weapon(Utils.getImg("rapier"), 2) {

    override val range: Int = 2
    override val area: Char = 'r'
    override val target: Char = 'e'

    override fun getDescription(): String {
        return "A long thin kind of sword." +
                "You can touch enemies from a long distance with this weapon."
    }
}