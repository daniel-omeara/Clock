package com.danielomeara.clock.features.timer.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.danielomeara.clock.features.timer.presentation.util.TimerActionButtonsState

@Composable
fun TimerActionButtons(
    enabledButtons: TimerActionButtonsState,
    startTimerClicked: () -> Unit,
    pauseTimerClicked: () -> Unit,
    stopTimerClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FloatingActionButton(
            onClick = {
                if(enabledButtons.isPlayEnabled) {
                    startTimerClicked()
                }
            },
            backgroundColor = if(enabledButtons.isPlayEnabled) MaterialTheme.colors.secondary else Color.Gray
        ) {
            Icon(Icons.Default.PlayArrow, "")
        }
        FloatingActionButton(
            onClick = {
                if(enabledButtons.isPauseEnabled) {
                    pauseTimerClicked()
                }
            },
            backgroundColor = if(enabledButtons.isPauseEnabled) MaterialTheme.colors.secondary else Color.Gray
        ) {
            Icon(Icons.Default.Pause, "")
        }
        FloatingActionButton(
            onClick = {
                if(enabledButtons.isStopEnabled) {
                    stopTimerClicked()
                }
            },
            backgroundColor = if(enabledButtons.isStopEnabled) MaterialTheme.colors.secondary else Color.Gray
        ) {
            Icon(Icons.Default.Stop, "")
        }
    }
}