package com.katzoft.archcomponents.sample.work

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.ktx.OneTimeWorkRequestBuilder
import java.util.*

interface RequestProvider {
    fun getRequest(millis: Long = 2500 + Random(System.currentTimeMillis()).nextInt(2500).toLong(),
                   tag: String = "general"): OneTimeWorkRequest
}

class SleepyRequestProvider : RequestProvider {
    override fun getRequest(millis: Long,
                            tag: String): OneTimeWorkRequest {
        val inputData: Data = Data.Builder()
                .putLong(SleepyWorker.SLEEP_TIME_MILLIS, millis)
                .build()
        return OneTimeWorkRequestBuilder<SleepyWorker>()
                .setInputData(inputData)
                .addTag(tag)
                .build()
    }
}