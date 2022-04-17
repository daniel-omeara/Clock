package com.danielomeara.clock.features.timer.presentation.util

import com.danielomeara.clock.features.timer.domain.models.TimerValue

sealed class TimerEvent {
    data class TimerValueClicked(val timerValue: TimerValue): TimerEvent()
    data class SelectedTimerValueChanged(val sliderValue: Double): TimerEvent()
    object StartTimer: TimerEvent()
    object PauseTimer: TimerEvent()
    object StopTimer: TimerEvent()
    object SaveTimer: TimerEvent()
    object InitTimer: TimerEvent()
}