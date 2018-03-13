package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Player
import ru.swalrus.rectdungeon.Items.Item

abstract class Potion (img: Texture, name: String) : Item(img, name) {

    override var actionName: String = "Drink"

    override fun land(creature: Creature?, caster: Creature) {
        if (creature != null) {
            cast(creature)
        }
    }

    override fun use(player: Player) {
        cast(player)
        player.removeItem(this)
    }

    abstract fun cast(creature: Creature)
}