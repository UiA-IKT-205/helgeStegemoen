package no.uia.ikt205.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView

    var timeToCountDownInMs = 5000L
    val timeTicks = 1000L
    val oneMinute = 60000L
    var isTimerRunning = false  //



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Code for SeekBar
        // Got help from:
        // https://abhiandroid.com/ui/seekbar (using SeekBar)
        // https://www.geeksforgeeks.org/seekbar-in-kotlin/ (progressChangedValue variable)
        // ## The app crashed every time i tried to use the TimerFunction. I tried to use a cast
        // such as setTimer(progressChangedValue as Long), and I think that is what caused probelms.
        // So I created a new function, so I created the castIntToLong() function, that did the
        // casting for me.*/
        // ToDo: Try to find out how to use cast
        val seek = findViewById<SeekBar>(R.id.seekBar)
        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            var progressChangedValue = 0
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                progressChangedValue = progress;
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Write custom code for progress is started
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity,
                "Progress is: " + progressChangedValue + "%",
                Toast.LENGTH_SHORT).show()
                castIntToLong(progressChangedValue)
                //setTimer(progressChangedValue)
            }
        })


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

        timer = object : CountDownTimer(timeToCountDownInMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                isTimerRunning = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }
        timer.start()
    }

    fun updateCountDownDisplay(timeInMs: Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

    fun setTimer(timeInMs: Long){
        timeToCountDownInMs = timeInMs
    }

    fun castIntToLong(myVar: Int){
        var longVariable = 1L * myVar * oneMinute
        setTimer(longVariable)
    }

}