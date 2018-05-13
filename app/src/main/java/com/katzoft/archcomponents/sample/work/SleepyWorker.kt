package com.katzoft.archcomponents.sample.work

import androidx.work.Worker
import timber.log.Timber

class SleepyWorker : Worker() {
    override fun doWork(): WorkerResult {
        Timber.d("Started $id")
        Thread.sleep(inputData.getLong(SLEEP_TIME_MILLIS, 1000))
        Timber.d("Finished $id")
        return WorkerResult.SUCCESS
    }

    companion object {
        const val SLEEP_TIME_MILLIS = "sleep_time"
    }
}
