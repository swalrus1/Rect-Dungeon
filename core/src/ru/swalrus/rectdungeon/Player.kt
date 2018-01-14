package ru.swalrus.rectdungeon

import com.badlogic.gdx.graphics.Texture

class Player (x: Int, y: Int, img: Texture, room: Room) : Creature(x, y, img, room) {

    var AP: Int = Const.MAX_AP

    override fun makeTurn() {
        ready = false
    }

    override fun endMove() {
        super.endMove()
        endAction()
    }


    fun endTurn() {
        AP = Const.MAX_AP
        ready = true
    }


    fun swipe (dir: Int) {
        if (!ready and !super.inAnim()) {
            move(dir)
        }
    }

    private fun endAction() {
        if (AP > 0) {
            ready = false
        } else {
            endTurn()
        }
    }

    ///////////////////////////////////////
    ///             ACTIONS             ///
    ///////////////////////////////////////

    fun makeAction(requiredAP: Int) : Boolean {
        if (AP >= requiredAP) {
            AP -= requiredAP
            return true
        } else {
            // TODO: Помигать ОД на панели
            return false
        }
    }

    override fun move(direction: Int, force: Boolean) {
        if (force or makeAction(1)) {
            super.move(direction, false)
        }
    }
}