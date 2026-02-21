package com.example.metronome

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var intervalValueText: TextView
    private lateinit var bpmValueText: TextView
    private lateinit var intervalSeekBar: SeekBar
    private lateinit var startStopButton: Button

    private val handler = Handler(Looper.getMainLooper())
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

    private var intervalMs = DEFAULT_INTERVAL_MS
    private var isRunning = false

    private val metronomeTick = object : Runnable {
        override fun run() {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, TONE_DURATION_MS)
            if (isRunning) {
                handler.postDelayed(this, intervalMs.toLong())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intervalValueText = findViewById(R.id.intervalValueText)
        bpmValueText = findViewById(R.id.bpmValueText)
        intervalSeekBar = findViewById(R.id.intervalSeekBar)
        startStopButton = findViewById(R.id.startStopButton)

        intervalSeekBar.max = MAX_INTERVAL_MS - MIN_INTERVAL_MS
        intervalSeekBar.progress = intervalMs - MIN_INTERVAL_MS

        updateIntervalDisplay(intervalMs)

        intervalSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                intervalMs = progress + MIN_INTERVAL_MS
                updateIntervalDisplay(intervalMs)

                if (isRunning) {
                    restartMetronome()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        startStopButton.setOnClickListener {
            if (isRunning) {
                stopMetronome()
            } else {
                startMetronome()
            }
        }
    }

    private fun updateIntervalDisplay(interval: Int) {
        intervalValueText.text = getString(R.string.interval_format_ms, interval)
        val bpm = 60000f / interval
        bpmValueText.text = getString(R.string.interval_format_bpm, bpm)
    }

    private fun startMetronome() {
        isRunning = true
        startStopButton.text = getString(R.string.stop)
        handler.post(metronomeTick)
    }

    private fun stopMetronome() {
        isRunning = false
        startStopButton.text = getString(R.string.start)
        handler.removeCallbacks(metronomeTick)
    }

    private fun restartMetronome() {
        handler.removeCallbacks(metronomeTick)
        handler.post(metronomeTick)
    }

    override fun onPause() {
        super.onPause()
        stopMetronome()
    }

    override fun onDestroy() {
        super.onDestroy()
        toneGenerator.release()
    }

    companion object {
        private const val MIN_INTERVAL_MS = 150
        private const val MAX_INTERVAL_MS = 2000
        private const val DEFAULT_INTERVAL_MS = 500
        private const val TONE_DURATION_MS = 80
    }
}
