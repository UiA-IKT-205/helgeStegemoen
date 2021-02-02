package no.uia.ikt205.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var pauseTimer:CountDownTimer
    lateinit var startButton:Button
    lateinit var countdownDisplay:TextView
    lateinit var repititionText:TextView

    var workCountDownTimeInMs = 5000L
    var pauseCountDownTimeInMs = 0L
    val timeTicks = 1000L
    val oneMinute = 60000L   // 60000L = one minute
    var isTimerRunning = false  //
    var numberOfRepititions:Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repititionText = findViewById<Button>(R.id.repititionText)
        // numberOfRepititions = repititionText.text.toString().toInt()

        /* Code for SeekBar
        // Got help from:
        // https://abhiandroid.com/ui/seekbar (using SeekBar)
        // https://www.geeksforgeeks.org/seekbar-in-kotlin/ (progressChangedValue variable)
        */
        val workSeekBar = findViewById<SeekBar>(R.id.workSeekBar)
        workSeekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            var workTimeInMinutes = 0
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                workTimeInMinutes = progress;
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Write custom code for progress is started
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity,
                "Progress is: " + workTimeInMinutes + "%",
                Toast.LENGTH_SHORT).show()
                setWorkTimer(oneMinute * workTimeInMinutes.toLong())
            }
        })

        // Pause countdown
        val pauseSeekBar = findViewById<SeekBar>(R.id.pauseSeekBar)
        pauseSeekBar?.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
            var workTimeInMinutes = 0
            override fun onProgressChanged(
                    seek: SeekBar,
                    progress: Int, fromUser: Boolean
            ) {
                workTimeInMinutes = progress;
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Write custom code for progress is started
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@MainActivity,
                        "Progress is: " + workTimeInMinutes + "%",
                        Toast.LENGTH_SHORT).show()
                setPauseTimer(oneMinute * workTimeInMinutes.toLong())
            }
        })


        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            numberOfRepititions = repititionText.text.toString().toInt()
            // Avoid running multiple timers at the same time
            if(!isTimerRunning){
                startCountDown(it)
            }

        }
        countdownDisplay = findViewById<TextView>(R.id.countDownView)


    }

    fun startCountDown(v: View){
        isTimerRunning = true

            timer = object : CountDownTimer(workCountDownTimeInMs, timeTicks) {
                override fun onFinish() {
                    Toast.makeText(this@MainActivity, "Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                    isTimerRunning = false
                    if(pauseCountDownTimeInMs > 0)
                        startPauseTimer(v)
                }

                override fun onTick(millisUntilFinished: Long) {
                    updateCountDownDisplay(millisUntilFinished)
                }
            }
            timer.start()
        }

        fun startPauseTimer(v: View){
            isTimerRunning = true

            pauseTimer = object : CountDownTimer(pauseCountDownTimeInMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Pausen er ferdig", Toast.LENGTH_SHORT).show()
                isTimerRunning = false
                if(numberOfRepititions > 0){
                    numberOfRepititions--
                    startCountDown(v)
                }
                isTimerRunning = false
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        pauseTimer.start()
    }

    fun updateCountDownDisplay(timeInMs: Long){
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

    fun setWorkTimer(workInMs: Long){
        workCountDownTimeInMs = workInMs
        // Updates the time when the timer is updated
        updateCountDownDisplay(workInMs)
    }
    fun setPauseTimer(pauseInMs: Long){
        pauseCountDownTimeInMs = pauseInMs
        // Updates the time when the timer is updated
        updateCountDownDisplay(pauseInMs)
    }


}