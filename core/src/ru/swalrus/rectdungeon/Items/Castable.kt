package ru.swalrus.rectdungeon.Items

interface Castable {

    val area: Char
    val range: Int
    val target: Char

    fun cast() = {}
}