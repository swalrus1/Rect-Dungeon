package ru.swalrus.rectdungeon.Items

import ru.swalrus.rectdungeon.Game.Creature

interface Castable {

    val area: Char
    val range: Int
    val target: Char
    val requiredAP: Int
    val resetAP: Boolean

    fun cast(x: Int, y: Int, attacker: Creature, defender: Creature?)
}