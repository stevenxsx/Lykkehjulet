package com.s205465.lykkehjulet

sealed class GameState {
    class Running(
        val lettersUsed: String,
        val underscoreWord: String,
        val drawable: Int,
        val category: String,
        val points: Int,
        val lives: Int,
        val spinResult: String
    ) : GameState()
    class Lost(val wordToGuess: String) : GameState()
    class Won(val wordToGuess: String) : GameState()
}
