package ru.swalrus.rectdungeon.Items

import ru.swalrus.rectdungeon.Game.Creature

interface castable {

    val area: Array<Array<Boolean>>
    val target: Char
    val requiredAP: Int
    val resetAP: Boolean

    fun cast(x: Int, y: Int, attacker: Creature, defender: Creature?)
}