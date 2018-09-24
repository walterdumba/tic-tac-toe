package kt

import kt.board.Board
import kt.board.BoardImpl
import kt.board.Cell
import kt.board.CellImpl


const val NUMBER_OF_ROWS =3
const val NUMBER_OF_COLS =3

class TicTacToeGame{


    private var gameBoard:Board<*> = BoardImpl( Array< Array< Cell<Char> > >(
            NUMBER_OF_ROWS,
            { Array(NUMBER_OF_COLS, { CellImpl() } ) }
    ))

    fun isEndGame():Boolean = ( this.gameBoard as BoardImpl ).end
    fun play()              = (this.gameBoard.play()        )
    fun show()              = (this.gameBoard.show()        )
    fun showGameResult()    = (this.gameBoard as BoardImpl  ).showGameResult()
    fun clearScreen()       = (0 until 50).forEach { println() }

}

fun main(args: Array<String>){

    var g = TicTacToeGame()

    while( !g.isEndGame() ){
        g.clearScreen()
        g.show()
        g.play()
    }
    g.show()
    g.showGameResult()
}