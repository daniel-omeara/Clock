package com.danielomeara.clock.features.timer.domain.models

import com.danielomeara.clock.features.timer.presentation.util.TimerState
import kotlinx.serialization.Serializable

@Serializable
data class Timer(
    val timerState: TimerState = TimerState.Stopped,
    val totalTimeInSeconds: Long = 0,
    var secondsRemaining: Long = 0
) {
    val hours: Long = secondsRemaining / 3600

    val minutes: Long = (secondsRemaining - (hours * 3600)) / 60

    val seconds: Long = secondsRemaining - (minutes * 60) - (hours * 3600)

    val hoursText: String = hours.toString()
        get() = if(field.length == 2) field else "0$field"

    val minutesText: String = minutes.toString()
        get() = if(field.length == 2) field else "0$field"

    val secondsText: String = seconds.toString()
        get() = if(field.length == 2) field else "0$field"

}