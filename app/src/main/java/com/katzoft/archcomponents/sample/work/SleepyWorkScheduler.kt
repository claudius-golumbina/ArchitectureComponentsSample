package com.katzoft.archcomponents.sample.work

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import androidx.work.WorkContinuation
import androidx.work.WorkManager
import androidx.work.WorkStatus

class SleepyWorkScheduler(private val requestProvider: RequestProvider) {

    fun observeWork(lifecycleOwner: LifecycleOwner, observer: Observer<List<WorkStatus>>) {
        scheduleWork().observe(lifecycleOwner, observer)
    }

    fun scheduleWork(): LiveData<List<WorkStatus>> {
        val workA1 = requestProvider.getRequest(tag = TAG_A1)
        val workA2 = requestProvider.getRequest(tag = TAG_A2)
        val workB = requestProvider.getRequest(tag = TAG_B)
        val workC = requestProvider.getRequest(tag = TAG_C)
        val workD = requestProvider.getRequest(tag = TAG_D)
        val workE = requestProvider.getRequest(tag = TAG_E)

        with(WorkManager.getInstance()) {
            val continuationAB = beginWith(workA1, workA2).then(workB)
            val continuationCD = beginWith(workC).then(workD)
            val continuationE = WorkContinuation.combine(continuationAB, continuationCD).then(workE)
            continuationE.enqueue()

            return continuationE.statuses
        }
    }

    fun cancelWork() {
        with(WorkManager.getInstance()) {
            ALL_TAGS.forEach { cancelAllWorkByTag(it) }
            cancelUniqueWork(TAG_SYNC)
        }
    }

    companion object {
        const val TAG_A1 = "a1"
        const val TAG_A2 = "a2"
        const val TAG_B = "b"
        const val TAG_C = "c"
        const val TAG_D = "d"
        const val TAG_E = "e"
        val ALL_TAGS = arrayOf(TAG_A1, TAG_A2, TAG_B, TAG_C, TAG_D, TAG_E)
        const val TAG_SYNC = "sync"
        const val TAG_AB = "ab"
        const val TAG_CD = "cd"
    }


}