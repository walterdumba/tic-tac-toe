package org.classicgames.tictactoe

import org.classicgames.tictactoe.model.Board
import org.classicgames.tictactoe.model.BoardImpl
import org.classicgames.tictactoe.model.CellImpl


class TicTacToeConsoleGame{

    private var gameBoard: Board<*> = BoardImpl( Array(3) { Array(3) { CellImpl() } })

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