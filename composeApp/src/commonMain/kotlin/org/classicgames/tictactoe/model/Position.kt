package org.classicgames.tictactoe.model

class Position{

    var row: Int =0
    var col: Int =0

    constructor()
    constructor(row: Int, col: Int){
        this.row = row
        this.col = col
    }

}