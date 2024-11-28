package org.classicgames.tictactoe.board

interface Board<T>{

    fun getCell(row: Int, col: Int):T
    fun setCell(row: Int, col: Int, value: T)
    fun play():Boolean
    fun show()
    fun getNumberOfRows():  Int
    fun getNumberOfCols():  Int
    fun getNumberOfCells(): Int
}

