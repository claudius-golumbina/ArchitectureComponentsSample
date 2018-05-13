package com.katzoft.archcomponents.sample

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidx.work.WorkStatus
import com.katzoft.archcomponents.sample.work.SleepyRequestProvider
import com.katzoft.archcomponents.sample.work.SleepyWorkScheduler

class MainViewModel : ViewModel() {
    private var data: LiveData<List<WorkStatus>>? = null

    fun getData(): LiveData<List<WorkStatus>>? {
        if (data == null) {
            data = MutableLiveData()
            loadData()
        }
        return data
    }

    private fun loadData() {
        data = SleepyWorkScheduler(requestProvider = SleepyRequestProvider()).scheduleWork()
    }
}