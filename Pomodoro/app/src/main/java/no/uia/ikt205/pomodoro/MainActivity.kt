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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thirtyMinuteButton = findViewById<Button>(R.id.thirtyMinutesButton)
        oneHourButton = findViewById<Button>(R.id.oneHourButton)
        ninetyMinutesButton = findViewById<Button>(R.id.ninetyMinutesButton)
        twoHoursButton = findViewById<Button>(R.id.twoHoursButton)

        thirtyMinuteButton.setOnClickListener(){
            setTimer(3000L)
        }
        oneHourButton.setOnClickListener(){
            setTimer(6000L)
        }
        ninetyMinutesButton.setOnClickListener(){
            setTimer(9000L)
        }
        twoHoursButton.setOnClickListener(){
            setTimer(12000L)
        }

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
           startCountDown(it)
        }
        coutdownDisplay = findViewById<TextView>(R.id.countDownView)


    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
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