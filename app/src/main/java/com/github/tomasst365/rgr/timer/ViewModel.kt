package com.github.tomasst365.rgr.timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel(
    private val liveDataToObserve: MutableLiveData<TimerStatus> = MutableLiveData()
): ViewModel() {

    fun getLiveData() = liveDataToObserve

}