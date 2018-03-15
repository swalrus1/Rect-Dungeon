package ru.swalrus.rectdungeon.Creatures

import com.badlogic.gdx.Gdx.app
import ru.swalrus.rectdungeon.Game.Creature
import ru.swalrus.rectdungeon.Game.Room
import ru.swalrus.rectdungeon.Items.Item
import ru.swalrus.rectdungeon.Utils

class Chest (x: Int, y: Int, val item: Item, room: Room) : Creature(x, y, 0, Utils.getImg("chest"), room) {

    override fun act() {

    }

    override fun onDeath() {
        // 'item' is null when onDeath is called in initialization
        if (item != null) {
            dropLoot(item)
        } else {
            app.error("Chest", "A chest is spawned in lava. This can cause bugs")
        }
    }

    override fun dealDamage(damage: Float, direction: Char) {
        die()
    }

    override fun die() {
        super.die()
        alive = true
        setSpriteImg(Utils.getImg("chest_open"))
    }
}