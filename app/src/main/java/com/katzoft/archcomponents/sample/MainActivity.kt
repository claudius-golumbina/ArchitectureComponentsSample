package com.katzoft.archcomponents.sample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import androidx.work.State
import androidx.work.WorkStatus
import com.katzoft.archcomponents.sample.work.SleepyWorkScheduler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

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

    private fun observer(): Observer<List<WorkStatus>> {
        return Observer {
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
    }

    private fun showSnackbar(it: WorkStatus) {
        Snackbar.make(fab, buildLogString(it), Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun log(it: WorkStatus) {
        Log.d("MainActivity", buildLogString(it))
    }

    private fun buildLogString(it: WorkStatus) = "Updated state is ${it.state.name} for ${it.tags}"

    private fun enableSwitch(tag: String) {
        when (tag) {
            SleepyWorkScheduler.TAG_A1 -> switchA1.isEnabled = true
            SleepyWorkScheduler.TAG_A2 -> switchA2.isEnabled = true
            SleepyWorkScheduler.TAG_B -> switchB.isEnabled = true
            SleepyWorkScheduler.TAG_C -> switchC.isEnabled = true
            SleepyWorkScheduler.TAG_D -> switchD.isEnabled = true
            SleepyWorkScheduler.TAG_E -> switchE.isEnabled = true
        }
    }

    private fun checkSwitch(tag: String) {
        when (tag) {
            SleepyWorkScheduler.TAG_A1 -> switchA1.isChecked = true
            SleepyWorkScheduler.TAG_A2 -> switchA2.isChecked = true
            SleepyWorkScheduler.TAG_B -> switchB.isChecked = true
            SleepyWorkScheduler.TAG_C -> switchC.isChecked = true
            SleepyWorkScheduler.TAG_D -> switchD.isChecked = true
            SleepyWorkScheduler.TAG_E -> switchE.isChecked = true
        }
    }
}
