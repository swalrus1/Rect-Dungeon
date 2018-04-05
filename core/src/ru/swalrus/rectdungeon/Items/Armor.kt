package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Game.Player

abstract class Armor (img: Texture, name: String) : Item(img, name), equippable {

    override var equipped: Boolean = false
    override var actionName: String = "Equip"
        get() {
            return if (equipped) {
                "Unequip"
            } else {
                "Equip"
            }
        }

    override fun use(player: Player) {
        player.armor = this
    }
}