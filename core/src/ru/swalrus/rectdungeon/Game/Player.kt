package ru.swalrus.rectdungeon.Game

import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Utils
import ru.swalrus.rectdungeon.Items.*
import com.badlogic.gdx.Gdx.app
import ru.swalrus.rectdungeon.Effects.Buff
import ru.swalrus.rectdungeon.HealPotion

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
        addItem(Bow())
        inventory[0]!!.use(this)
        addItem(HealPotion())
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


    fun swipe (dir: Char) {
        if (!ready and !super.inAnim()) {
            actionQueue.add({ move(dir) })
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
            0 -> if (throwItem != null) {
                actionQueue.add({ throwItem(throwItem!!, x, y) })
            }
            1 -> if (leftHand != null) {
                leftHand!!.cast(x, y, this, room.getCreatureAt(x, y))
            }
            2 -> if (rightHand != null) {
                rightHand!!.cast(x, y, this, room.getCreatureAt(x, y))
            }
        }
    }

    fun endAction() {
        if (AP > 0) {
            ready = false
        } else {
            reallyEndTurn()
        }
    }

    override fun endTurn() {
        // Prevent standard turn finishing if the action queue is empty
        // If it is really needed to end turn, use 'reallyEndTurn()'
    }

    fun reallyEndTurn() {
        actionQueue.push({ ready = true })
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

    fun removeItem(item: Item) {
        var i = 0
        while (i < inventory.size && inventory[i] != item) {
            i++
        }
        if (i < inventory.size) {
            inventory[i] = null
            when (item) {
                leftHand -> leftHand = null
                rightHand -> rightHand = null
                armor -> armor = null
                // todo
            }
        } else {
            app.error("Inventory", "Attempt to remove item that are not in the inventory")
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
                    else -> {
                        if (rightHand != null) {
                            unequip(rightHand!!)
                        }
                        rightHand = item
                    }
                }
                is Armor -> {
                    if (armor != null) {
                        unequip(armor!!)
                    }
                    armor = item
                }
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
            if (item is equippable) {
                item.equipped = true
            }
        } else {
            app.error("Inventory", "Attempt to quip item not being stored in the inventory")
        }
    }

    fun unequip(item: Item) {
        when (item) {
            is Weapon -> {
                if (leftHand == item) {
                    leftHand = null
                } else if (rightHand == item) {
                    rightHand = null
                }
            }
            is Armor -> {
                if (armor == item) {
                    armor = null
                }
            }
            else -> app.error("Inventory", "Item that is being unequipped is not supportable")
        }
        if (item is equippable) {
            item.equipped = false
        }
    }

    fun throwButtonPressed(item: Item) {
        throwItem = item
        // TODO: Edit parameters
        room.setYellowArea(x-1, y-1, 'r', 'a', 5)
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

    override fun throwItem(item: Item, x: Int, y: Int) {
        if (makeAction(1)) {
            super.throwItem(item, x, y)
            removeItem(item)
        }
    }
}