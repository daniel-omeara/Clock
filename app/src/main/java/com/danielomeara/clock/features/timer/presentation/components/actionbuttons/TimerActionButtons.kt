package com.danielomeara.clock.features.timer.presentation.components.actionbuttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.danielomeara.clock.R
import com.danielomeara.clock.features.timer.presentation.util.TimerActionButtonsState

@Composable
fun TimerActionButtons(
    enabledButtons: TimerActionButtonsState,
    startTimerClicked: () -> Unit,
    pauseTimerClicked: () -> Unit,
    stopTimerClicked: () -> Unit,
) {
    TimerActionButton(
        isEnabled = enabledButtons.isPlayEnabled,
        icon = Icons.Default.PlayArrow,
        onClick = startTimerClicked,
        contentDescription = stringResource(R.string.timer_action_button_play_content_desc)
    )
    TimerActionButton(
        isEnabled = enabledButtons.isPauseEnabled,
        icon = Icons.Default.Pause,
        onClick = pauseTimerClicked,
        contentDescription = stringResource(R.string.timer_action_button_pause_content_desc)
    )
    TimerActionButton(
        isEnabled = enabledButtons.isStopEnabled,
        icon = Icons.Default.Stop,
        onClick = stopTimerClicked,
        contentDescription = stringResource(R.string.timer_action_button_stop_content_desc)
    )
}