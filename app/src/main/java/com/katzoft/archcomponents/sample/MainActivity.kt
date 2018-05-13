package com.katzoft.archcomponents.sample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.Switch
import androidx.work.State
import androidx.work.WorkStatus
import com.katzoft.archcomponents.sample.work.SleepyWorkScheduler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var sleepyWorkScheduler: SleepyWorkScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        connectViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun connectViewModel() {
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getData()?.observe(this, observer())
    }

    private fun observer(): Observer<List<WorkStatus>> = Observer {
        it?.let {
            it.forEach { log(it) }
            it.filter { it.state == State.RUNNING }.forEach { it.tags.forEach { enableSwitch(it) } }
            it.filter { it.state.isFinished }.forEach {
                it.tags.forEach {
                    enableSwitch(it) //just in case we didn't get the running state
                    checkSwitch(it)
                }
            }
        }
    }

    private fun showSnackbar(it: WorkStatus) {
        Snackbar.make(fab, buildLogString(it), Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun log(it: WorkStatus) {
        Timber.d(buildLogString(it))
    }

    private fun buildLogString(it: WorkStatus) = "Updated state is ${it.state.name} for ${it.tags}"

    private fun enableSwitch(tag: String) {
        getSwitch(tag)?.isEnabled = true
    }

    private fun checkSwitch(tag: String) {
        getSwitch(tag)?.isChecked = true
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
