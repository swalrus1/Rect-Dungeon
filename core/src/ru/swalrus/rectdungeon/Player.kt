package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture

class Player (x: Int, y: Int, img: Texture, room: Room) : Creature(x, y, img, room) {

    override fun makeTurn() {
        ready = false
    }

    fun swipe (dir: Int) {
        if (!ready and !super.inAnim()) {
            move(dir)
        }
    }
}