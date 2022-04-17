package com.danielomeara.clock.features.timer.presentation.components.timer

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.danielomeara.clock.features.timer.domain.models.Timer
import com.danielomeara.clock.features.timer.domain.models.TimerValue

@Composable
fun SetAndShowTimerTextView(
    timer: Timer,
    isInTimerSetMode: Boolean,
    currentSelectedTimerValue: TimerValue,
    setTimerValueClicked: (TimerValue) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TimerText(
            text = timer.hoursText,
            isInTimerSetMode = isInTimerSetMode,
            isSelected = currentSelectedTimerValue == TimerValue.Hours,
            onClick = { setTimerValueClicked(TimerValue.Hours) }
        )
        TimerText(":")
        TimerText(
            text = timer.minutesText,
            isInTimerSetMode = isInTimerSetMode,
            isSelected = currentSelectedTimerValue == TimerValue.Minutes,
            onClick = { setTimerValueClicked(TimerValue.Minutes) }
        )
        TimerText(":")
        TimerText(
            text = timer.secondsText,
            isInTimerSetMode = isInTimerSetMode,
            isSelected = currentSelectedTimerValue == TimerValue.Seconds,
            onClick = { setTimerValueClicked(TimerValue.Seconds) }
        )
    }
}