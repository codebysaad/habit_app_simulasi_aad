package com.dicoding.habitapp.ui.countdown

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.notification.NotificationWorker
import com.dicoding.habitapp.utils.HABIT
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import java.util.concurrent.TimeUnit

class CountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        supportActionBar?.title = "Count Down"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val habit = intent.getParcelableExtra<Habit>(HABIT) as Habit

        findViewById<TextView>(R.id.tv_count_down_title).text = habit.title

        val viewModel = ViewModelProvider(this).get(CountDownViewModel::class.java)

        //TODO 10 : Set initial time and observe current time. Update button state when countdown is finished
        val tvCountDown = findViewById<TextView>(R.id.tv_count_down)
        viewModel.setInitialTime(habit.minutesFocus)
        viewModel.currentTimeString.observe(this) { counting ->
            tvCountDown.text = counting
        }

        //TODO 13 : Start and cancel One Time Request WorkManager to notify when time is up.
        viewModel.eventCountDownFinish.observe(this) { state ->
            updateButtonState(state)
        }

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            val data = workDataOf(HABIT_ID to habit.id, HABIT_TITLE to habit.title)
            val time = viewModel.getTime()
            viewModel.startTimer()
            updateButtonState(false)

            val notify: WorkRequest =
                OneTimeWorkRequestBuilder<NotificationWorker>().setInputData(data)
                    .setInitialDelay(time!!, TimeUnit.MILLISECONDS).build()
            WorkManager.getInstance(this).enqueue(notify)
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            viewModel.resetTimer()
            updateButtonState(true)
            WorkManager.getInstance(this).cancelAllWork()
        }
    }

    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = !isRunning
    }
}