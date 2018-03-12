package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture
import ru.swalrus.rectdungeon.Game.Player

abstract class Artefact (img: Texture, name: String) : Item(img, name) {

    // TODO edit
    override var actionName: String = "Use"

    override fun use(player: Player) {
        // TODO
    }
}