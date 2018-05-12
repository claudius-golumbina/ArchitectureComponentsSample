package com.example.claudius.myapplication

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.work.State
import androidx.work.WorkStatus
import com.example.claudius.myapplication.work.SleepyRequestProvider
import com.example.claudius.myapplication.work.SleepyWorkScheduler
import com.example.claudius.myapplication.work.SleepyWorkScheduler.Companion.TAG_A1
import com.example.claudius.myapplication.work.SleepyWorkScheduler.Companion.TAG_A2
import com.example.claudius.myapplication.work.SleepyWorkScheduler.Companion.TAG_B
import com.example.claudius.myapplication.work.SleepyWorkScheduler.Companion.TAG_C
import com.example.claudius.myapplication.work.SleepyWorkScheduler.Companion.TAG_D
import com.example.claudius.myapplication.work.SleepyWorkScheduler.Companion.TAG_E
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var sleepyWorkScheduler: SleepyWorkScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        sleepyWorkScheduler = SleepyWorkScheduler(SleepyRequestProvider(), this, Observer {
            it?.let {
                it.forEach { Log.d("SleepyWorker", buildLogString(it)) }
                it.filter { it.state == State.RUNNING }.forEach { it.tags.forEach { enableSwitch(it) } }
                it.filter { it.state.isFinished }.forEach { it.tags.forEach { checkSwitch(it) } }
            }
        })
        sleepyWorkScheduler.scheduleWork()
    }

    override fun onDestroy() {
        sleepyWorkScheduler.cancelWork()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings ->
                return true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSnackbar(it: WorkStatus) {
        Snackbar.make(fab, buildLogString(it), Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun buildLogString(it: WorkStatus) = "Updated state is ${it.state.name} for ${it.tags}"

    private fun enableSwitch(tag: String) {
        when (tag) {
            TAG_A1 -> switchA1.isEnabled = true
            TAG_A2 -> switchA2.isEnabled = true
            TAG_B -> switchB.isEnabled = true
            TAG_C -> switchC.isEnabled = true
            TAG_D -> switchD.isEnabled = true
            TAG_E -> switchE.isEnabled = true
        }
    }

    private fun checkSwitch(tag: String) {
        when (tag) {
            TAG_A1 -> switchA1.isChecked = true
            TAG_A2 -> switchA2.isChecked = true
            TAG_B -> switchB.isChecked = true
            TAG_C -> switchC.isChecked = true
            TAG_D -> switchD.isChecked = true
            TAG_E -> switchE.isChecked = true
        }
    }
}
