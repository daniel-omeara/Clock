package com.danielomeara.clock.features.timer.presentation.util

data class TimerActionButtonsState(
    val isPlayEnabled: Boolean,
    val isPauseEnabled: Boolean,
    val isStopEnabled: Boolean,
    val isInTimerSetMode: Boolean
)
