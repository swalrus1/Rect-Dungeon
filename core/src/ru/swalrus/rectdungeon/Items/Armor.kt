package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Game.Player

abstract class Armor (img: Texture, id: Int, name: String) : Item(img, id, name) {

    override var actionName: String = "Equip"

    override fun use(player: Player) {
        player.armor = this
    }
}