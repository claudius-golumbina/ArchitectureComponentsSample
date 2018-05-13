package com.katzoft.archcomponents.sample.main.view

import android.arch.lifecycle.Observer
import androidx.work.State
import androidx.work.WorkStatus
import com.katzoft.archcomponents.sample.Presenter
import timber.log.Timber

class MainPresenter : Presenter<MainView> {
    override var view: MainView? = null

    override fun bind(view: MainView) {
        this.view = view
    }

    override fun unbind() {
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