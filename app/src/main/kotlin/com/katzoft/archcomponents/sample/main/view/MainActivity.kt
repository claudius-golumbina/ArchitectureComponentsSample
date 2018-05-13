package com.katzoft.archcomponents.sample.main.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Switch
import com.katzoft.archcomponents.sample.R
import com.katzoft.archcomponents.sample.main.viewmodel.MainViewModel
import com.katzoft.archcomponents.sample.work.SleepyWorkScheduler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        bindPresenter()
        connectViewModel()
    }

    override fun onDestroy() {
        unbindPresenter()
        super.onDestroy()
    }

    override fun enableSwitch(tag: String) {
        getSwitch(tag)?.isEnabled = true
    }

    override fun checkSwitch(tag: String) {
        getSwitch(tag)?.isChecked = true
    }

    private fun bindPresenter() {
        presenter = MainPresenter()
        presenter.bind(this)
    }

    private fun unbindPresenter() {
        presenter.unbind()
    }

    private fun connectViewModel() {
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getData()?.observe(this, presenter.observer())
    }

    private fun getSwitch(tag: String): Switch? = when (tag) {
        SleepyWorkScheduler.TAG_A1 -> switchA1
        SleepyWorkScheduler.TAG_A2 -> switchA2
        SleepyWorkScheduler.TAG_B -> switchB
        SleepyWorkScheduler.TAG_C -> switchC
        SleepyWorkScheduler.TAG_D -> switchD
        SleepyWorkScheduler.TAG_E -> switchE
        else -> null
    }
}
