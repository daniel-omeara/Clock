package com.danielomeara.clock.features.timer.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.danielomeara.clock.core.util.rememberWindowInfo
import com.danielomeara.clock.features.timer.domain.viewmodel.TimerViewModel
import com.danielomeara.clock.features.timer.presentation.components.actionbuttons.ActionButtonsView
import com.danielomeara.clock.features.timer.presentation.components.actionbuttons.TimerActionButtons
import com.danielomeara.clock.features.timer.presentation.components.timer.TimerView
import com.danielomeara.clock.features.timer.presentation.util.TimerEvent

@Composable
fun TimerScreen(
    timerViewModel: TimerViewModel = hiltViewModel()
) {
    val enabledButtons = timerViewModel.enabledButtons.value

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                timerViewModel.onEvent(TimerEvent.InitTimer)
            } else if (event == Lifecycle.Event.ON_PAUSE) {
                timerViewModel.onEvent(TimerEvent.SaveTimer)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val windowInfo = rememberWindowInfo()
    when(windowInfo.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Column {
                TimerView(
                    modifier = Modifier.weight(0.8f),
                    timer = timerViewModel.timer.value,
                    isInTimerSetMode = enabledButtons.isInTimerSetMode,
                    currentSelectedTimerValue = timerViewModel.currentSelectedTimerValue.value,
                    setTimerValueClicked = { timerViewModel.onEvent(TimerEvent.TimerValueClicked(it)) },
                    sliderValue = if(enabledButtons.isInTimerSetMode) timerViewModel.sliderValue.value else timerViewModel.timerProgress.value,
                    onSelectedTimerValueChange = { timerViewModel.onEvent(TimerEvent.SelectedTimerValueChanged(it)) }
                )
                ActionButtonsView(
                    modifier = Modifier.weight(0.2f),
                    content = {
                        TimerActionButtons(
                            enabledButtons = enabledButtons,
                            startTimerClicked = { timerViewModel.onEvent(TimerEvent.StartTimer) },
                            pauseTimerClicked = { timerViewModel.onEvent(TimerEvent.PauseTimer) },
                            stopTimerClicked = { timerViewModel.onEvent(TimerEvent.StopTimer) }
                        )
                    }
                )
            }
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row {
                TimerView(
                    modifier = Modifier.weight(0.8f),
                    timer = timerViewModel.timer.value,
                    isInTimerSetMode = enabledButtons.isInTimerSetMode,
                    currentSelectedTimerValue = timerViewModel.currentSelectedTimerValue.value,
                    setTimerValueClicked = { timerViewModel.onEvent(TimerEvent.TimerValueClicked(it)) },
                    sliderValue = if(enabledButtons.isInTimerSetMode) timerViewModel.sliderValue.value else timerViewModel.timerProgress.value,
                    onSelectedTimerValueChange = { timerViewModel.onEvent(TimerEvent.SelectedTimerValueChanged(it)) }
                )
                ActionButtonsView(
                    modifier = Modifier.weight(0.2f),
                    content = {
                        TimerActionButtons(
                            enabledButtons = enabledButtons,
                            startTimerClicked = { timerViewModel.onEvent(TimerEvent.StartTimer) },
                            pauseTimerClicked = { timerViewModel.onEvent(TimerEvent.PauseTimer) },
                            stopTimerClicked = { timerViewModel.onEvent(TimerEvent.StopTimer) }
                        )
                    }
                )
            }
        }
    }
}