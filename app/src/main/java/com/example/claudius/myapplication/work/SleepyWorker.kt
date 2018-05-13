package com.example.claudius.myapplication.work

import android.util.Log
import androidx.work.Worker

class SleepyWorker : Worker() {
    override fun doWork(): WorkerResult {
        Log.d("SleepyWorker", "Started $id")
        Thread.sleep(inputData.getLong(SLEEP_TIME_MILLIS, 1000))
        Log.d("SleepyWorker", "Finished $id")
        return WorkerResult.SUCCESS
    }

    companion object {
        const val SLEEP_TIME_MILLIS = "sleep_time"
    }
}
