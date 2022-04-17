package com.danielomeara.clock.features.timer.domain.models

sealed class TimerValueScaleFactor(val scaleFactor: Double) {
    object HourScaleFactor: TimerValueScaleFactor(3.6)
    object MinuteAndSecondScaleFactor: TimerValueScaleFactor(6.0)
}