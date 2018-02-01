package ru.swalrus.rectdungeon.Items

import com.badlogic.gdx.graphics.Texture

abstract class Weapon(img: Texture, id: Int) : Item(img, id), Castable {

}