package org.classicgames.tictactoe.model

import org.classicgames.tictactoe.NUMBER_OF_COLS
import java.util.regex.Pattern


const val SCORE:Int                    =10
const val BESTVALUE_MINMAX_ALGORITHM   =10000
const val INDEX_OF_ROW_STDIN           =0
const val INDEX_OF_COL_STDIN           =2

class BoardImpl(board: Array< Array<Cell<Char>> >  ): AbstractBoard<Cell<Char>>(board) {


    var end :Boolean         = false
    var isHumanTurns:Boolean = true
    var turnScore:Int        = 0


    override fun play(): Boolean {
        lateinit var opponentMove:String
        if(this.isHumanTurns){
            do {
                println("Player 'O' please make your turn e.g. 1, 2 and enter in the end!")
                print(">")
                opponentMove = readLine()!!
            }while( !isValidPlay(opponentMove) )

            //Human turns
            var row:Int = opponentMove[INDEX_OF_ROW_STDIN].minus('0').toInt()
            var col:Int = opponentMove[INDEX_OF_COL_STDIN].minus('0').toInt()
            this.getCell(row, col).setValue(BALL)
            println("Human turns at ($row, $col)")
            isHumanTurns=false
        } else{
            //Machine turns
            var machineMove = findMachineBestMove()
            this.getCell(machineMove.row, machineMove.col).setValue(CROSS)
            println("Machine turns at (${machineMove.row}, ${machineMove.col})")
            isHumanTurns =true
        }
        this.checkForWins()
        return true
    }



    override fun show() {
        print(this)
    }

    fun showGameResult(){
        when(turnScore){
            SCORE -> { println("Congrats Machine won!")   }
            -SCORE ->{ println("Congrats Human won!")     }
            else->  { println("No wins!")                }
        }
    }

    override fun toString(): String {
        val sbBuilder = StringBuilder()
        for (row in 0 until this.getNumberOfRows()) {
            sbBuilder.append("|")
            for (col in 0 until this.getNumberOfCols()) {
                sbBuilder
                        .append(this.getCell(row, col))
                        .append("|")
            }
            sbBuilder.append("\n")
        }
        return sbBuilder.toString()
    }

    /********************************* PRIVATE PART **********************************************/

    private fun evaluate(currentBoardState: Array<Array<Cell<Char>>>): Int {
        //Evaluate whether X or O wins
        this.turnScore = evaluateRows(currentBoardState)
        if (this.turnScore == 0) {
            this.turnScore = evaluateCols(currentBoardState)
        }
        if (this.turnScore == 0) {
            this.turnScore = evaluateDiagonals(currentBoardState)
        }
        return this.turnScore
    }

    private fun evaluateRows(currentBoardState: Array<Array<Cell<Char>>>): Int {
        for (row in currentBoardState.indices) {
            if (currentBoardState[row][0].getValue() == currentBoardState[row][1].getValue()
                    && currentBoardState[row][1].getValue() == currentBoardState[row][2].getValue()) {
                if (currentBoardState[row][0].getValue() == CROSS) {
                    return SCORE
                } else if (currentBoardState[row][0].getValue() == BALL) {
                    return -SCORE
                }
            }
        }
        return 0
    }
    private fun evaluateCols(currentBoardState: Array<Array<Cell<Char>>>): Int {
        for (col in 0 until currentBoardState[0].size) {
            if (currentBoardState[0][col].getValue() == currentBoardState[1][col].getValue()
                    && currentBoardState[1][col].getValue() == currentBoardState[2][col].getValue()) {
                if (currentBoardState[0][col].getValue() == CROSS) {
                    return SCORE
                } else if (currentBoardState[0][col].getValue() == BALL) {
                    return -SCORE
                }
            }
        }
        return 0
    }

    private fun evaluateDiagonals(currentBoardState: Array<Array<Cell<Char>>>): Int {
        if (currentBoardState[0][0].getValue() == currentBoardState[1][1].getValue()
                && currentBoardState[1][1].getValue() == currentBoardState[2][2].getValue()) {
            if (currentBoardState[0][0].getValue() == CROSS) {
                return SCORE
            } else if (currentBoardState[0][0].getValue() == BALL) {
                return -SCORE
            }
        }
        if (currentBoardState[0][2].getValue() == currentBoardState[1][1].getValue()
                && currentBoardState[1][1].getValue() == currentBoardState[2][0].getValue()) {
            if (currentBoardState[0][2].getValue() == CROSS) {
                return SCORE
            } else if (currentBoardState[0][2].getValue() == BALL) {
                return -SCORE
            }
        }
        return 0
    }

    private fun checkForWins(): Int {
        val turnsScore = evaluate(this.board)
        //Maximizer wins the game
        return when (turnsScore) {
            SCORE -> {
                this.end =true
                turnsScore
            }
        //Minimizer wins
            -SCORE -> {
                this.end=true
                turnsScore
            }
        //Neither maximizer nor minimizer wins its a tie
            else -> 0
        }
    }

    /**
     *
     * @param gBoard
     * @param depth
     * @param isMaximizingPlayer true: if you want to Machine maximize the gains,
     * false: if you want the minimize the loses
     * @return
     */
    private fun minmax(gBoard: BoardImpl, depth: Int, isMaximizingPlayer: Boolean): Int {

        val turnsScore = evaluate(gBoard.board)
        //Maximizer wins the game
        when {
            turnsScore == SCORE -> return turnsScore
            turnsScore == -SCORE -> return turnsScore
            !hasMovesLeft( gBoard.board ) -> return 0
            isMaximizingPlayer -> {
                var bestValue = -BESTVALUE_MINMAX_ALGORITHM
                for (row in 0 until gBoard.getNumberOfRows()) {
                    for (col in 0 until gBoard.getNumberOfCols()) {
                        if (gBoard.getCell(row, col).isEmpty()) {
                            gBoard.getCell(row, col).setValue(CROSS)
                            val value = minmax(gBoard, depth + 1, false)
                            bestValue = Math.max(bestValue, value)
                            gBoard.getCell(row, col).setEmpty() //Undo de assignment
                        }
                    }
                }
                return bestValue - depth
            }
            else -> {
                var bestValue = BESTVALUE_MINMAX_ALGORITHM
                for (row in 0 until gBoard.getNumberOfRows() ) {
                    for (col in 0 until gBoard.getNumberOfCols() ) {
                        if (gBoard.getCell(row, col).isEmpty() ) {
                            gBoard.getCell(row, col).setValue(BALL)
                            val value = minmax(gBoard, depth + 1, true)
                            bestValue = Math.min(bestValue, value)
                            gBoard.getCell(row, col).setEmpty() //Undo de assignment
                        }
                    }
                }
                return bestValue + depth
            }
        }
        //is minimizingPlayer
    }

    private fun findMachineBestMove(): Position {
        var bestVal = -BESTVALUE_MINMAX_ALGORITHM
        val currBestMove = Position(-1, -1)
        for (row in 0 until this.getNumberOfRows()) {
            for (col in 0 until this.getNumberOfCols()) {
                if ( this.getCell(row, col).isEmpty() ) {
                    this.getCell(row, col).setValue(CROSS)
                    val moveVal = minmax(this, 0, false)
                    this.getCell(row, col).setEmpty()
                    if (moveVal > bestVal) {
                        currBestMove.row = row
                        currBestMove.col = col
                        bestVal = moveVal
                    }
                }
                //Good turnsScore = 10, and because its one of machine best moves
            }
        }
        return currBestMove
    }

    private fun isValidPlay(opponentMove: String): Boolean {
        if (opponentMove.length != NUMBER_OF_COLS) {
            return false
        }
        //TODO: Improve the validation
        val inputPattern = "\\d{1},\\d{1}"
        val inputChecker = Pattern.compile(inputPattern)
        if (!inputChecker.matcher(opponentMove).matches()) {
            return false
        }
        val row = Integer.parseInt(opponentMove[0].toString())
        val col = Integer.parseInt(opponentMove[2].toString())
        return this.getCell(row, col).isEmpty()
    }

    private fun hasMovesLeft(board: Array<Array<Cell<Char>>>): Boolean {
        for (row in board.indices) {
            for (col in board[0].indices) {
                if (board[row][col].isEmpty()) {
                    return true
                }
            }
        }
        return false
    }
}