package com.katzoft.archcomponents.sample.main.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import androidx.work.WorkStatus
import com.katzoft.archcomponents.sample.Presenter
import com.katzoft.archcomponents.sample.work.SleepyRequestProvider
import com.katzoft.archcomponents.sample.work.SleepyWorkScheduler

class MainModelPresenter : Presenter<MainModelView> {
    override var view: MainModelView? = null
    private var data: LiveData<List<WorkStatus>>? = null

    override fun bind(view: MainModelView) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

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