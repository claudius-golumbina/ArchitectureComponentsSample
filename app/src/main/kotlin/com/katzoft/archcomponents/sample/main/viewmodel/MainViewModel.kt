package com.katzoft.archcomponents.sample.main.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import androidx.work.WorkStatus

class MainViewModel : ViewModel(), MainModelView {
    private var presenter = MainModelPresenter()

    init {
        presenter.bind(this)
    }

    override fun onCleared() {
        presenter.unbind()
        super.onCleared()
    }

    fun getData(): LiveData<List<WorkStatus>>? = presenter.getData()
}