package ru.swalrus.rectdungeon.Effects

import ru.swalrus.rectdungeon.Game.Creature

abstract class Buff {

    abstract val effects: Map<Char, Int>
    abstract var infinite: Boolean
    abstract var time: Int                      // How many turns the effect would occur

    open fun onTurn(creature: Creature) {}

    fun getEffect(name: Char) : Int {
        if (name in effects) {
            return effects[name]!!
        } else {
            return 0
        }
    }
}