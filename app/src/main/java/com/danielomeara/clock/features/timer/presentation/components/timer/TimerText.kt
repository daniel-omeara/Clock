package com.danielomeara.clock.features.timer.presentation.components.timer

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle

@Composable
fun TimerText(
    text: String,
    modifier: Modifier = Modifier,
    isInTimerSetMode: Boolean = false,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    style: TextStyle = MaterialTheme.typography.h3
) {
    val color: Color = if(isSelected && isInTimerSetMode) MaterialTheme.colors.secondary else Color.Black
    Text(
        text = text,
        style = style,
        modifier = modifier.clickable(isInTimerSetMode, null, Role.Button) {
            onClick()
        },
        color = color
    )
}