package org.classicgames.tictactoe.model


const val EMPTY =' '
const val BALL  ='O'
const val CROSS ='X'

class CellImpl: Cell<Char> {


    var content = EMPTY

    override fun getValue(): Char = this.content
    override fun setValue(value: Char) {
        this.content =value
    }
    override fun isEmpty(): Boolean = this.content == EMPTY
    override fun setEmpty() {
        this.content= EMPTY
    }

    override fun toString(): String {
        return "${content}"
    }

}