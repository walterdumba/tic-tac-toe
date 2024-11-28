package org.classicgames.tictactoe.board

interface Cell<T> {

    fun getValue():T
    fun setValue(value: T)
    fun isEmpty():Boolean
    fun setEmpty()
}