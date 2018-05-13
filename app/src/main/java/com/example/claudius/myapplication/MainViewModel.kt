package com.example.claudius.myapplication

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import androidx.work.WorkStatus
import com.example.claudius.myapplication.work.SleepyRequestProvider
import com.example.claudius.myapplication.work.SleepyWorkScheduler

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