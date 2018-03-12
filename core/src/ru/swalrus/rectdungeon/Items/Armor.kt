package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Game.Player

abstract class Armor (img: Texture, name: String) : Item(img, name) {

    override var actionName: String = "Equip"

    override fun use(player: Player) {
        player.armor = this
    }
}