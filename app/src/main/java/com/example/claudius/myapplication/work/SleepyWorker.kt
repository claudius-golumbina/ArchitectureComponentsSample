package com.example.claudius.myapplication.work

import android.util.Log
import androidx.work.Worker

class SleepyWorker : Worker() {
    override fun doWork(): WorkerResult {
        Log.d("SleepyWorker", "Started")
        Thread.sleep(inputData.getLong(SLEEP_TIME_MILLIS, 1000))
        Log.d("SleepyWorker", "Finished")
        return WorkerResult.SUCCESS
    }

    companion object {
        const val SLEEP_TIME_MILLIS = "sleep_time"
    }
}
