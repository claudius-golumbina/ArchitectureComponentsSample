package com.katzoft.archcomponents.sample.main.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import androidx.work.WorkStatus
import com.katzoft.archcomponents.sample.Presenter
import com.katzoft.archcomponents.sample.work.SleepyRequestProvider
import com.katzoft.archcomponents.sample.work.SleepyWorkScheduler
import timber.log.Timber

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
            loadData()
        }
        return data
    }

    private fun loadData() {
        data = Transformations.map(doLoadData(),
                {
                    it.map {
                        log(it)
                        it
                    }
                })
    }

    private fun doLoadData() =
            SleepyWorkScheduler(requestProvider = SleepyRequestProvider()).scheduleWork()

    private fun log(it: WorkStatus) = Timber.d(buildLogString(it))

    private fun buildLogString(it: WorkStatus) = "Updated state is ${it.state.name} for ${it.tags}"
}