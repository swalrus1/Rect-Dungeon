package ru.swalrus.rectdungeon.Game

import ru.swalrus.rectdungeon.Const
import ru.swalrus.rectdungeon.Utils
import ru.swalrus.rectdungeon.Items.*
import com.badlogic.gdx.Gdx.app
import ru.swalrus.rectdungeon.Area
import ru.swalrus.rectdungeon.Items.HealPotion

class Player (x: Int, y: Int, HP: Int, room: Room) : Creature(x, y, 6, Utils.getImg("human"), room) {

    var AP: Int = Const.MAX_AP                                          // Action Points
    var maxHP = HP
    var inventory: Array<Item?> = arrayOfNulls(Const.INVENTORY_SIZE)
    var extraSlot: Item? = null                                         // An additional slot of the inventory

    var armor: Armor? = null
    var rightHand: Weapon? = null                                       // A weapon hold in the right hand
    var leftHand: Weapon? = null                                        // A weapon hold in the left hand


    init {
        addItem(Dagger())
        inventory[0]!!.use(this)
        addItem(HealPotion())
    }


    override fun act() {
        ready = false
        // Reset AP
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


    // Handle swiped
    fun swipe (dir: Char) {
        if (!ready and !super.inAnim()) {
            actionQueue.add({ move(dir) })
        }
    }

    // Handle button clicks
    fun onButtonTouch(id: Int) {
        when (id) {
            1 -> if (leftHand != null) {
                room.setYellowArea(x-1, y-1, leftHand!!.area, leftHand!!.target)
            }
            2 -> if (rightHand != null) {
                room.setYellowArea(x-1, y-1, rightHand!!.area, rightHand!!.target)
            }
        }
    }

    // Cast weapon from given hand (id)
    // If 'id' == 0, throws an item (костыль)
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

    // Called when an action is finished
    fun endAction() {
        if (AP > 0) {
            ready = false
        } else {
            reallyEndTurn()
        }
    }

    override fun endTurn() {
        // Prevent standard turn finishing if the action queue is empty
        // If it really need to end turn, use 'reallyEndTurn()'
    }

    // Finish turn. Certainly
    // More specific alternative to 'Creature's 'endTurn'
    fun reallyEndTurn() {
        actionQueue.push({ ready = true })
    }


    // Add a new item to the inventory
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

    // Remove an item from the inventory
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

    // Removes an item from the extra slot
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

    // Equips the given item
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

    // Unequips an item
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

    // Called when throw button is pressed
    fun throwButtonPressed(item: Item) {
        throwItem = item
        // TODO: Edit parameters
        room.setYellowArea(x-1, y-1, Area.range(5), 'a')
    }

    ///////////////////////////////////////
    ////////        ACTIONS        ////////
    ///////////////////////////////////////

    // Returns 'true' if player is able to make an action requiring 'requiredAP'
    // Also subtracts AP
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

    // Move player
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

    // Make player to attack
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

    // Throw an item
    override fun throwItem(item: Item, x: Int, y: Int) {
        if (makeAction(1)) {
            super.throwItem(item, x, y)
            removeItem(item)
        }
    }

    fun drinkPotion(potion: Potion) {
        if (makeAction(1)) {
            potion.cast(this)
            removeItem(potion)
        }
    }

    // Equips an item from the inventory with the given index
    fun equip(i: Int) {
        if (makeAction(1)) {
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
    }
}