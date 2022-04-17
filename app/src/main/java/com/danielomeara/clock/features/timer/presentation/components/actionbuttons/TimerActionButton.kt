package com.danielomeara.clock.features.timer.presentation.components.actionbuttons

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TimerActionButton(
    isEnabled: Boolean,
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null,
    backgroundColor: Color = if(isEnabled) MaterialTheme.colors.secondary else Color.Gray
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = backgroundColor
    ) {
        Icon(icon, contentDescription)
    }
}