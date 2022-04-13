package com.danielomeara.clock.features.timer.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.danielomeara.clock.features.timer.domain.viewmodel.TimerViewModel
import com.danielomeara.clock.features.timer.presentation.components.RoundSlider
import com.danielomeara.clock.features.timer.presentation.components.TimerActionButtons
import com.danielomeara.clock.features.timer.presentation.components.TimerView
import com.danielomeara.clock.features.timer.presentation.util.TimerEvent

@Composable
fun TimerScreen(
    timerViewModel: TimerViewModel = hiltViewModel()
) {
    val enabledButtons = timerViewModel.enabledButtons.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        if(enabledButtons.isInTimerSetMode) {
            val angle = remember {
                mutableStateOf(0.0)
            }
            val timerText = (angle.value / 6.0).toInt().toString()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text("Hours")
                }

                Button(onClick = { /*TODO*/ }) {
                    Text("Minutes")
                }

                Button(onClick = { /*TODO*/ }) {
                    Text("Seconds")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.50f),
                contentAlignment = Alignment.Center
            ) {
                RoundSlider(
                    angle
                )


                Text(
                    text = "00:00:$timerText",
                    style = MaterialTheme.typography.h5
                )
            }
            FloatingActionButton(
                onClick = {
                    if(enabledButtons.isPlayEnabled) {
                        timerViewModel.onEvent(TimerEvent.StartTimer)
                    }
                },
                backgroundColor = if(enabledButtons.isPlayEnabled) MaterialTheme.colors.secondary else Color.Gray
            ) {
                Icon(Icons.Default.PlayArrow, "")
            }
        } else {
            TimerView(
                timerProgress = timerViewModel.timerProgress.value,
                timerText = timerViewModel.timerText.value
            )

            TimerActionButtons(
                enabledButtons = enabledButtons,
                startTimerClicked = { timerViewModel.onEvent(TimerEvent.StartTimer) },
                pauseTimerClicked = { timerViewModel.onEvent(TimerEvent.PauseTimer) },
                stopTimerClicked = { timerViewModel.onEvent(TimerEvent.StopTimer) }
            )
        }
    }
}