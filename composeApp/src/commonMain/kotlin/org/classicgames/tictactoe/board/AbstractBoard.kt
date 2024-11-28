package org.classicgames.tictactoe.board

import java.util.*

abstract class AbstractBoard<T>(board: Array<Array<T>>) : Board<T> {


    var board:Array< Array<T> > = board


    override fun getCell(row: Int, col: Int): T {
        return this.board[row][col]
    }

    override fun setCell(row: Int, col: Int, value: T) {
        this.board[row][col]=value
    }
    override fun getNumberOfRows(): Int {
        return this.board.size
    }
    override fun getNumberOfCols(): Int {
        return this.board[0].size
    }

    override fun getNumberOfCells(): Int {
        return this.getNumberOfCols()*this.getNumberOfCols()
    }

    override fun toString(): String {
        return "AbstractBoard(board=${Arrays.toString(board)})"
    }


}