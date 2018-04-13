package ru.swalrus.rectdungeon.Effects

import ru.swalrus.rectdungeon.Game.Creature

abstract class Buff {

    abstract val effects: Map<Char, Int>        // Map of (name of effect parameter, power of the parameter)
    abstract var infinite: Boolean              // Must the effect ignore the timer
    abstract var time: Int                      // How many turns the effect would occur

    // Called when a creature with the buff starts its turn
    open fun onTurn(creature: Creature) {}

    // Returns power of the requested effect parameter
    fun getEffect(name: Char) : Int {
        if (name in effects) {
            return effects[name]!!
        } else {
            return 0
        }
    }
}