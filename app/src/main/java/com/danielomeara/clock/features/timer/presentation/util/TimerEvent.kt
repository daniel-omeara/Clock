package com.danielomeara.clock.features.timer.presentation.util

sealed class TimerEvent {
    object StartTimer: TimerEvent()
    object PauseTimer: TimerEvent()
    object StopTimer: TimerEvent()
}