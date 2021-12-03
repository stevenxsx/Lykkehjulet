package com.s205465.lykkehjulet

import android.util.Log
import kotlin.random.Random

class GameManager {

    private lateinit var lettersUsed: String
    private lateinit var underscoreWord: String
    private lateinit var wordToGuess: String
    private var spinResult: String = ""
    private val maxTries = 7
    private var currentTries = 0
    private var drawable: Int = R.drawable.game0
    private val baselinePointValue: Int = 1000
    var canGuessLetter: Boolean = false
    private var category: String = "Category: Countries"
    private var points: Int = 0
    private var lives: Int = 0

    fun startNewGame(): GameState {
        lives = 5
        points = 0
        lettersUsed = ""
        currentTries = 0
        drawable = R.drawable.game7
        val randomIndex = Random.nextInt(0, GameConstants.words.size)
        wordToGuess = GameConstants.words[randomIndex]
        generateUnderscores(wordToGuess)
        return getGameState()
    }

    fun spin(): GameState {
        if (!canGuessLetter) {
            val randomInt = Random.nextInt(0, 20)
            when (randomInt) {
                1 -> {
                    Log.wtf("1", "ekstra spin"); lives++
                    spinResult = "Ekstra Spin :)"
                }
                2 -> {
                    Log.wtf("1", "tabt spin"); lives--
                    spinResult = "Tabt Spin :("
                }
                3 -> {
                    Log.wtf("1", "gå fallit"); points = 0
                    spinResult = "Gå Fallit :("
                }
                else -> {
                    Log.wtf("1", "score += $baselinePointValue * $randomInt")
                    spinResult = (randomInt * baselinePointValue).toString()
                    canGuessLetter = true
                }

            }
        }
        return getGameState()

    }

    fun generateUnderscores(word: String) {
        val sb = StringBuilder()
        word.forEach { char ->
            if (char == '/') {
                sb.append(' ')
            } else {
                sb.append("_")
            }
        }
        underscoreWord = sb.toString()
    }

    fun play(letter: Char): GameState {

        if (canGuessLetter) { //Makes sure you have spun the wheel and landed on a monetary value
            //before allowing the letter buttons to function
                //redundant if the check is made in the onClickListener
                canGuessLetter = false

            //irrelevant statement if the letters are removed after using them
            if (lettersUsed.contains(letter)) {
                return GameState.Running(
                    lettersUsed,
                    underscoreWord,
                    drawable,
                    category,
                    points,
                    lives,
                    spinResult
                )
            }

            lettersUsed += letter
            val indexes = mutableListOf<Int>()

            wordToGuess.forEachIndexed { index, char ->
                if (char.equals(letter, true)) {
                    indexes.add(index)
                }
            }

            var finalUnderscoreWord = "" + underscoreWord // _ _ _ _ _ _ _ -> E _ _ _ _ _ _
            indexes.forEach { index ->
                val sb = StringBuilder(finalUnderscoreWord).also { it.setCharAt(index, letter) }
                finalUnderscoreWord = sb.toString()
            }

            if (indexes.isEmpty()) {
                //currentTries++
                lives--
            }
            //awards points for number of letters guessed
            points += Integer.parseInt(spinResult) * indexes.size

            underscoreWord = finalUnderscoreWord

        }
        return getGameState()
    }

    private fun getHangmanDrawable(): Int {
        return when (currentTries) {
            0 -> R.drawable.game0
            1 -> R.drawable.game1
            2 -> R.drawable.game2
            3 -> R.drawable.game3
            4 -> R.drawable.game4
            5 -> R.drawable.game5
            6 -> R.drawable.game6
            7 -> R.drawable.game7
            else -> R.drawable.game7
        }
    }

    private fun getGameState(): GameState {
        if (underscoreWord.equals(wordToGuess, true)) {
            return GameState.Won(wordToGuess)
        }

        /*if (currentTries == maxTries) {
            return GameState.Lost(wordToGuess)
        }*/
        if (lives <= 0) {
            return GameState.Lost(wordToGuess)
        }

        drawable = getHangmanDrawable()
        return GameState.Running(lettersUsed, underscoreWord, drawable, category, points, lives,spinResult)
    }
}