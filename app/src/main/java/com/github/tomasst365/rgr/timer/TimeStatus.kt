package com.github.tomasst365.rgr.timer

sealed class TimerStatus{
    data class OneSecondPassed(val currentTime: Int): TimerStatus()
    object TimeOver: TimerStatus()
}
