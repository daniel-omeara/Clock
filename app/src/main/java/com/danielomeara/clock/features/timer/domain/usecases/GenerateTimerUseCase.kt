package com.danielomeara.clock.features.timer.domain.usecases

import com.danielomeara.clock.features.timer.domain.models.Timer

class GenerateTimerUseCase {
    operator fun invoke(secondsRemaining: Long): Timer {
        val hoursUntilFinished = secondsRemaining / 3600
        val minutesUntilFinished = (secondsRemaining - (hoursUntilFinished * 3600)) / 60
        val secondsUntilFinished = secondsRemaining - (minutesUntilFinished * 60)
        return Timer(hoursUntilFinished, minutesUntilFinished, secondsUntilFinished)
    }
}