package com.github.tomasst365.rgr.timer

import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class Timer(
    private val fragment: Fragment,
    private val timer: TextView,
) {

    private var time: Int = 0
    private lateinit var viewModel: ViewModel
    private var timerListener: TimerListener? = null

    companion object {
        const val SECOND_IN_MILLIS = 1000L
    }

    init {
        initViewModel()
    }

    fun setOnTimerListener(listener: TimerListener) {
        timerListener = listener
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(fragment)
            .get(ViewModel::class.java)

        viewModel
            .getLiveData()
            .observe(
                fragment.viewLifecycleOwner,
                { timerStatus: TimerStatus -> renderData(timerStatus) })

    }

    fun start(seconds: Int) {
        val livedata = viewModel.getLiveData()
        time = seconds
        onStart()
        //сначала отправлись данные через liveData, но when сравнивает два object-класса плохо(работало через раз)
        Thread {
            while (time > 0) {
                livedata.postValue(TimerStatus.OneSecondPassed(time))
                Thread.sleep(SECOND_IN_MILLIS)
                time--
            }
            livedata.postValue(TimerStatus.TimeOver)
        }.start()

    }

    private fun onStart() {
        if (timerListener != null) {
            timerListener!!.onTimeStart()
        }
    }

    private fun renderData(timerStatus: TimerStatus) {
        when (timerStatus) {
            is TimerStatus.OneSecondPassed -> {
                timer.text = time.toString()
            }
            is TimerStatus.TimeOver -> {
                if (timerListener != null) {
                    timerListener!!.onTimeOver()
                }
            }
        }
    }

}