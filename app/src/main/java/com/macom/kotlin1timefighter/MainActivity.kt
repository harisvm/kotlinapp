package com.macom.kotlin1timefighter


import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {
    internal lateinit var tapMeButton: Button
    internal lateinit var score: TextView
    internal lateinit var timeLeft: TextView
    internal var scoreInt = 0
    internal lateinit var countDownTimer: CountDownTimer
    internal val timeLimit: Long = 60000
    internal val timeDecrement: Long = 1000
    internal var gameStarted = false
    internal var timeFinished: Long = 0
    internal lateinit var resetButton: Button
    var scoreOnReset: Int = 0
    var timeLeftOnReset: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tapMeButton = findViewById(R.id.tapme)
        score = findViewById(R.id.score)
        timeLeft = findViewById(R.id.timeLeft)
        resetButton = findViewById(R.id.reset)
        tapMeButton.setOnClickListener { v ->
            incrementScore()
        }
        resetButton.setOnClickListener { v ->
        }
        if (savedInstanceState != null) {
            scoreOnReset = savedInstanceState.getInt("score")
            timeLeftOnReset = timeLimit-savedInstanceState.getInt("countdown").toLong()
            restoreButton()
        } else {
            resetGame()
        }
    }


    private fun restoreButton() {
        score.text = scoreOnReset.toString()
        timeLeft.text = timeLeftOnReset.toString()


        countDownTimer = object : CountDownTimer(timeLeftOnReset , timeDecrement) {
            override fun onFinish() {
                gameStarted = false
                FancyToast.makeText(
                    this@MainActivity,
                    "Your Score is " + scoreInt,
                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show()

            }

            override fun onTick(millisUntilFinished: Long) {

                timeLeft.text = (millisUntilFinished / 1000).toString()
                timeFinished = millisUntilFinished
            }


        }
        countDownTimer.start()
        gameStarted = true
    }

    private fun incrementScore() {
        if (!gameStarted) {
            resetGame()
            gameStarted = true
        }
        scoreInt++
        score.text = scoreInt.toString()
    }

    private fun resetGame() {
        countDownTimer = object : CountDownTimer(timeLimit, timeDecrement) {
            override fun onFinish() {
                gameStarted = false
                FancyToast.makeText(
                    this@MainActivity,
                    "Your Score is " + scoreInt,
                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false
                ).show()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeft.text = (millisUntilFinished / 1000).toString()
                timeFinished = millisUntilFinished
            }
        }.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("score", scoreInt)
        outState.putInt("countdown", timeFinished.toInt() / 1000)
        countDownTimer.cancel()
    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        scoreOnReset = savedInstanceState.getInt("score")
//        timeLeft.text = timeLeftOnReset.toString()
//        timeLeftOnReset= savedInstanceState.getInt("countdown").toLong()
//        gameStarted = true
//        timeLeft.text = timeLeftOnReset.toString()
//        timeLeft.text = timeLeftOnReset.toString()
//        resetGame()
//        countDownTimer.start()
//
//
//
//    }
}