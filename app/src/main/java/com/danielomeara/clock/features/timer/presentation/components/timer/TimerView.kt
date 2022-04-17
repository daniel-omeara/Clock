package com.danielomeara.clock.features.timer.presentation.components.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.danielomeara.clock.features.timer.domain.models.Timer
import com.danielomeara.clock.features.timer.domain.models.TimerValue

@Composable
fun TimerView(
    modifier: Modifier = Modifier,
    timer: Timer,
    isInTimerSetMode: Boolean,
    currentSelectedTimerValue: TimerValue,
    setTimerValueClicked: (TimerValue) -> Unit,
    sliderValue: Double,
    onSelectedTimerValueChange: (Double) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RoundSlider(sliderValue = sliderValue, onSelectedTimerValueChange = onSelectedTimerValueChange, isSetTimerMode = isInTimerSetMode)

        SetAndShowTimerTextView(
            timer = timer,
            isInTimerSetMode = isInTimerSetMode,
            currentSelectedTimerValue = currentSelectedTimerValue,
            setTimerValueClicked = setTimerValueClicked
        )
    }
}