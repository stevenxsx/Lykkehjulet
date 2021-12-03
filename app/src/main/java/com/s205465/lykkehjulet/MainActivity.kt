package com.s205465.lykkehjulet

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private val gameManager = GameManager()

    private lateinit var wordTextView: TextView
    private lateinit var lettersUsedTextView: TextView
    //private lateinit var imageView: ImageView
    private lateinit var gameLostTextView: TextView
    private lateinit var gameWonTextView: TextView
    private lateinit var newGameButton: Button
    private lateinit var lettersLayout: ConstraintLayout
    private lateinit var spinButton: Button
    private lateinit var spinResultTextView: TextView
    private lateinit var categoryTextView: TextView
    private lateinit var pointsTextView: TextView
    private lateinit var livesTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        //imageView = findViewById(R.id.imageView)
        wordTextView = findViewById(R.id.wordTextView)
        lettersUsedTextView = findViewById(R.id.lettersUsedTextView)
        //gameLostTextView = findViewById(R.id.gameLostTextView)
        //gameWonTextView = findViewById(R.id.gameWonTextView)
        newGameButton = findViewById(R.id.newGameButton)
        lettersLayout = findViewById(R.id.lettersLayout)
        spinButton = findViewById(R.id.spinButton)
        spinResultTextView = findViewById(R.id.spinResultTextView)
        categoryTextView = findViewById(R.id.categoryTextView)
        pointsTextView = findViewById(R.id.pointsTextView)
        livesTextView = findViewById(R.id.livesTextview)

        newGameButton.setOnClickListener {
            startNewGame()
        }
        spinButton.setOnClickListener {
            spin()
        }

        val gameState = gameManager.startNewGame()
        updateUI(gameState)

        lettersLayout.children.forEach { letterView ->
            if (letterView is TextView) {
                letterView.setOnClickListener {
                    if (gameManager.canGuessLetter) {
                        val gameState = gameManager.play((letterView).text[0])
                        updateUI(gameState)
                        letterView.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun updateUI(gameState: GameState) {
        when (gameState) {
            is GameState.Lost -> showGameLost(gameState.wordToGuess)
            is GameState.Running -> { showGameRunning(
                gameState.lettersUsed, gameState.underscoreWord,
                gameState.drawable, gameState.category, gameState.points, gameState.lives,gameState.spinResult)}
            is GameState.Won -> showGameWon(gameState.wordToGuess)
        }
    }

    private fun showGameRunning(
        lettersUsed: String,
        underscoreWord: String,
        drawable: Int,
        category: String,
        points: Int,
        lives: Int,
        spinResult: String
    ) {
        wordTextView.text = underscoreWord
        lettersUsedTextView.text = "Letters used: ${lettersUsed}"
        categoryTextView.text = category
        pointsTextView.text = "Points: ${points}"
        livesTextView.text = "Lives: ${lives}"
        spinResultTextView.text = "Spin Result: ${spinResult}"
        //imageView.setImageDrawable(ContextCompat.getDrawable(this, gameState.drawable))
    }

    private fun showGameLost(wordToGuess: String) {
        Log.wtf("Game Ended","LOST")
        wordTextView.text = wordToGuess
        gameLostTextView.visibility = View.VISIBLE
        //imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.game7))
        spinButton.visibility = View.GONE
        lettersLayout.visibility = View.GONE
    }

    private fun showGameWon(wordToGuess: String) {
        Log.wtf("Game Ended","WON")
        wordTextView.text = wordToGuess
        gameWonTextView.visibility = View.VISIBLE
        spinButton.visibility = View.GONE
        lettersLayout.visibility = View.GONE
    }

    private fun startNewGame() {
        gameLostTextView.visibility = View.GONE
        gameWonTextView.visibility = View.GONE
        val gameState = gameManager.startNewGame()
        spinButton.visibility = View.VISIBLE
        lettersLayout.visibility = View.VISIBLE
        lettersLayout.children.forEach { letterView ->
            letterView.visibility = View.VISIBLE
        }
        updateUI(gameState)
    }

    private fun spin() {
        val gameState = gameManager.spin()
        updateUI(gameState)
    }

}