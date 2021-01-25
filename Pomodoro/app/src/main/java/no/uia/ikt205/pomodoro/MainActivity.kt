package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var thirtyMinuteButton:Button
    lateinit var oneHourButton:Button
    lateinit var ninetyMinutesButton:Button
    lateinit var twoHoursButton:Button
    lateinit var coutdownDisplay:TextView

    var timeToCountDownInMs = 5000L
    val timeTicks = 1000L
    val oneMinute = 60000L
    var isTimerRunning = false  //



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thirtyMinuteButton = findViewById<Button>(R.id.thirtyMinutesButton)
        oneHourButton = findViewById<Button>(R.id.oneHourButton)
        ninetyMinutesButton = findViewById<Button>(R.id.ninetyMinutesButton)
        twoHoursButton = findViewById<Button>(R.id.twoHoursButton)

        thirtyMinuteButton.setOnClickListener(){ setTimer(30*oneMinute) }
        oneHourButton.setOnClickListener(){ setTimer(60*oneMinute) }
        ninetyMinutesButton.setOnClickListener(){ setTimer(90*oneMinute) }
        twoHoursButton.setOnClickListener(){ setTimer(120*oneMinute) }

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            // Avoid running multiple timers at the same time
            if(!isTimerRunning){
                startCountDown(it)
            }

        }
        coutdownDisplay = findViewById<TextView>(R.id.countDownView)


    }

    fun startCountDown(v: View){
        isTimerRunning = true

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                isTimerRunning = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }
        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

    fun setTimer(timeInMs:Long){
        timeToCountDownInMs = timeInMs
    }

}