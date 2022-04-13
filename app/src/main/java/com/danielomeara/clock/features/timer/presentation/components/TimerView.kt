package com.danielomeara.clock.features.timer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.danielomeara.clock.features.timer.domain.models.TimerText

@Composable
fun TimerView(
    timerProgress: Float,
    timerText: TimerText,
    modifier: Modifier = Modifier,
    timerTextStyle: TextStyle = MaterialTheme.typography.h3,
    onTimerTextClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.clickable {
            onTimerTextClick()
        },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = timerProgress,
            modifier = Modifier.size(
                Dp(timerTextStyle.fontSize.value * 7)
            ),
            strokeWidth = 10.dp
        )
        Text(
            text = "${timerText.hours}:${timerText.minutes}:${timerText.seconds}",
            style = timerTextStyle
        )
    }
}