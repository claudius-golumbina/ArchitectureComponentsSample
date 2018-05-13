package com.katzoft.archcomponents.sample.main

import android.arch.lifecycle.Observer
import androidx.work.State
import androidx.work.WorkStatus
import timber.log.Timber

class MainPresenter {
    private var view: MainView? = null

    fun bind(view: MainView) {
        this.view = view
    }

    fun unbind() {
        view = null
    }

    fun observer(): Observer<List<WorkStatus>> = Observer {
        it?.let {
            it.forEach { log(it) }
            it.filter { it.state == State.RUNNING }.forEach {
                it.tags.forEach { view?.enableSwitch(it) }
            }
            it.filter { it.state.isFinished }.forEach {
                it.tags.forEach {
                    view?.enableSwitch(it) //just in case we didn't get the running state
                    view?.checkSwitch(it)
                }
            }
        }
    }

    private fun log(it: WorkStatus) {
        Timber.d(buildLogString(it))
    }

    private fun buildLogString(it: WorkStatus) = "Updated state is ${it.state.name} for ${it.tags}"


}