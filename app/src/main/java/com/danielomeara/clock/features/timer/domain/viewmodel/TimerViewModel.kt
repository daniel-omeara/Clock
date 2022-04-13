package com.danielomeara.clock.features.timer.domain.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.danielomeara.clock.features.timer.domain.models.TimerText
import com.danielomeara.clock.features.timer.domain.usecases.GenerateTimerUseCase
import com.danielomeara.clock.features.timer.presentation.util.TimerEvent
import com.danielomeara.clock.features.timer.presentation.util.TimerActionButtonsState
import com.danielomeara.clock.features.timer.presentation.util.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val generateTimerUseCase: GenerateTimerUseCase
): ViewModel() {

    private val _enabledButtons = mutableStateOf(
        TimerActionButtonsState(isPlayEnabled = true, isPauseEnabled = false, isStopEnabled = false, isInTimerSetMode = true)
    )
    val enabledButtons: State<TimerActionButtonsState> = _enabledButtons

    private var secondsRemaining: Long = 60L

    private val _timerText = mutableStateOf(generateTimerUseCase(secondsRemaining).mapToTimerText())
    val timerText: State<TimerText> = _timerText

    private val _timerProgress = mutableStateOf(1f)
    val timerProgress: State<Float> = _timerProgress

    private lateinit var timer: CountDownTimer

    private var timerLengthSeconds: Long = 60L

    private var timerState = TimerState.Stopped

    init {
        updateCountdownUI()
    }

    fun onEvent(timerEvent: TimerEvent) {
        when(timerEvent) {
            is TimerEvent.StartTimer -> {
                timerLengthSeconds = timerText.value.mapToTimer().getSecondsRemaining()
                secondsRemaining = timerText.value.mapToTimer().getSecondsRemaining()
                startTimer()
                updateButtons()
            }
            is TimerEvent.PauseTimer -> {
                timer.cancel()
                timerState = TimerState.Paused
                updateButtons()
            }
            is TimerEvent.StopTimer -> {
                timer.cancel()
                onTimerFinished()
            }
        }
    }

    private fun updateButtons() {
        when(timerState) {
            TimerState.Running -> {
                _enabledButtons.value = TimerActionButtonsState(
                    isPlayEnabled = false,
                    isPauseEnabled = true,
                    isStopEnabled = true,
                    isInTimerSetMode = false
                )
            }
            TimerState.Paused -> {
                _enabledButtons.value = TimerActionButtonsState(
                    isPlayEnabled = true,
                    isPauseEnabled = false,
                    isStopEnabled = true,
                    isInTimerSetMode = false
                )
            }
            TimerState.Stopped -> {
                _enabledButtons.value = TimerActionButtonsState(
                    isPlayEnabled = true,
                    isPauseEnabled = false,
                    isStopEnabled = false,
                    isInTimerSetMode = true
                )
            }
        }
    }

    private fun startTimer() {
        timerState = TimerState.Running
        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }

            override fun onFinish() {
                onTimerFinished()
            }
        }.start()
    }

    private fun updateCountdownUI() {
        _timerText.value = generateTimerUseCase(secondsRemaining).mapToTimerText()
        _timerProgress.value = ((timerLengthSeconds - secondsRemaining).toInt() / timerLengthSeconds).toFloat()
    }

    private fun onTimerFinished() {
        timerState = TimerState.Stopped
        secondsRemaining = timerLengthSeconds
        updateButtons()
        updateCountdownUI()
    }

}