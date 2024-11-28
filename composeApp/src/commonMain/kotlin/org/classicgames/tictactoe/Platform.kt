package org.classicgames.tictactoe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform