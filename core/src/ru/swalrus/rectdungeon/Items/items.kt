package ru.swalrus.rectdungeon.Items

import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Utils

class Arrow(val direction: Char, val landFun: (creature: Creature?, caster: Creature, dir: Char) -> Unit) :
        Item(Utils.getImg("arrow", direction), "Arrow") {

    override var actionName: String = ""

    override fun getDescription(): String {
        return "It seems, something goes wrong"
    }

    override fun land(creature: Creature?, caster: Creature) {
        landFun(creature, caster, direction)
    }

    override fun use(player: Player) {}
}