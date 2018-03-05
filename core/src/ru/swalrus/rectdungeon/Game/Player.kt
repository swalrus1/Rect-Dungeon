package ru.swalrus.rectdungeon.Game

import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Utils
import ru.swalrus.rectdungeon.Items.*
import com.badlogic.gdx.Gdx.app

class Player (x: Int, y: Int, HP: Int, room: Room) : Creature(x, y, 6, Utils.getImg("human"), room) {

    var AP: Int = Const.MAX_AP
    var maxHP = HP
    var inventory: Array<Item?> = arrayOfNulls(Const.INVENTORY_SIZE)
    var extraSlot: Item? = null

    var armor: Armor? = null
    var rightHand: Weapon? = null
    var leftHand: Weapon? = null


    init {
        // ShortSword, Rapier
        addItem(ShortSword())
        addItem(Rapier())
        inventory[0]!!.use(this)
    }


    override fun act() {
        ready = false
        AP = Const.MAX_AP
    }

    override fun endMove() {
        super.endMove()
        endAction()
    }

    override fun endAttack() {
        super.endAttack()
        endAction()
    }


    fun endTurn() {
        ready = true
    }


    fun swipe (dir: Char) {
        if (!ready and !super.inAnim()) {
            move(dir)
        }
    }

    fun onButtonTouch(id: Int) {
        when (id) {
            1 -> if (leftHand != null) {
                room.setYellowArea(x-1, y-1, leftHand!!.area, leftHand!!.target, leftHand!!.range)
            }
            2 -> if (rightHand != null) {
                room.setYellowArea(x-1, y-1, rightHand!!.area, rightHand!!.target, rightHand!!.range)
            }
        }
    }

    fun cast(id: Int, x: Int, y: Int) {
        when (id) {
            1 -> if (leftHand != null) {
                leftHand!!.cast(x, y, this, room.getCreatureAt(x, y))
            }
            2 -> if (rightHand != null) {
                rightHand!!.cast(x, y, this, room.getCreatureAt(x, y))
            }
        }
    }

    private fun endAction() {
        if (AP > 0) {
            ready = false
        } else {
            endTurn()
        }
    }


    fun addItem(item: Item) {
        if (null in inventory) {
            var i = 0
            while (inventory[i] != null) {
                i++
            }
            inventory[i] = item
        } else {
            extraSlot = item
            app.log("debug", "Added item to the extra slot")
            // TODO: Анимировать инвентарь
        }
    }

    fun clearExtraSlot() {
        if (null in inventory) {
            var i = 0
            while (inventory[i] != null) {
                i++
            }
            inventory[i] = extraSlot
        }
        extraSlot = null
    }

    fun equip(i: Int) {
        val item = inventory[i]
        if (item != null) {
            when (item) {
                is Weapon -> when (leftHand) {
                    null -> leftHand = item
                    else -> rightHand = item
                }
                is Armor -> armor = item
            }
        } else {
            app.error("Inventory", "Attempt to equip nothing (null)")
        }
    }

    fun equip(item: Item) {
        var i = 0
        while (i < inventory.size && inventory[i] != item) {
            i++
        }
        if (i < inventory.size) {
            equip(i)
        } else {
            app.error("Inventory", "Attempt to quip item not being stored in the inventory")
        }
    }

    ///////////////////////////////////////
    ////////        ACTIONS        ////////
    ///////////////////////////////////////

    fun makeAction(requiredAP: Int) : Boolean {
        room.resetYellowArea()
        if (AP >= requiredAP) {
            AP -= requiredAP
            return true
        } else {
            // TODO: Помигать ОД на панели
            return false
        }
    }

    override fun move(direction: Char, force: Boolean) : Boolean {
        if (force or makeAction(1)) {
            val isCorrect = super.move(direction, force)
            if (!isCorrect) {
                // Restore AP
                AP += 1
                endAction()
                return false
            } else {
                return true
            }
        } else {
            return false
        }
    }

    override fun attack(direction: Char, target: Creature,
                        afterAttack: (attacker: Creature, defender: Creature) -> Unit,
                        requiredAP: Int, resetAP: Boolean) {
        if (makeAction(requiredAP)) {
            if (resetAP) {
                AP = 0
            }
            super.attack(direction, target, afterAttack, requiredAP, resetAP)
        }
    }
}