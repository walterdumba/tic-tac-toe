package org.classicgames.tictactoe

import org.classicgames.tictactoe.model.Board
import org.classicgames.tictactoe.model.BoardImpl
import org.classicgames.tictactoe.model.CellImpl
import org.classicgames.tictactoe.model.NUMBER_OF_COLS
import org.classicgames.tictactoe.model.NUMBER_OF_ROWS


class TicTacToeConsoleGame{


    private var gameBoard: Board<*> = BoardImpl( Array(NUMBER_OF_ROWS) { Array(NUMBER_OF_COLS) { CellImpl() } })

    fun isEndGame():Boolean = ( this.gameBoard as BoardImpl).end
    fun play()              = (this.gameBoard.play()        )
    fun show()              = (this.gameBoard.show()        )
    fun showGameResult()    = (this.gameBoard as BoardImpl).showGameResult()
    fun clearScreen()       = (0 until 50).forEach { println() }

}

fun main() {

    var g = TicTacToeConsoleGame()

    while( !g.isEndGame() ){
        g.clearScreen()
        g.show()
        g.play()
    }
    g.show()
    g.showGameResult()
}