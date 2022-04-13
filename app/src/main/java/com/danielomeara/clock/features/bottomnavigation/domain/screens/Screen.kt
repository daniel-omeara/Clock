package com.danielomeara.clock.features.bottomnavigation.domain.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Watch
import androidx.compose.ui.graphics.vector.ImageVector
import com.danielomeara.clock.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Timer : Screen("timerscreen", R.string.timer, Icons.Default.Timer)
    object Stopwatch : Screen("stopwatchscreen", R.string.stopwatch, Icons.Default.Watch)
}

val screens = listOf(
    Screen.Timer,
    Screen.Stopwatch,
)