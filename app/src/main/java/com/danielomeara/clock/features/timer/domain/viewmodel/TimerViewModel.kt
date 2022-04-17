package com.danielomeara.clock.features.timer.domain.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielomeara.clock.features.timer.data.datastore.TimerDatastore
import com.danielomeara.clock.features.timer.domain.models.Timer
import com.danielomeara.clock.features.timer.domain.models.TimerValue
import com.danielomeara.clock.features.timer.domain.models.TimerValueScaleFactor
import com.danielomeara.clock.features.timer.presentation.util.TimerEvent
import com.danielomeara.clock.features.timer.presentation.util.TimerActionButtonsState
import com.danielomeara.clock.features.timer.presentation.util.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timerDatastore: TimerDatastore
): ViewModel() {

    private val _enabledButtons = mutableStateOf(
        TimerActionButtonsState(isPlayEnabled = true, isPauseEnabled = false, isStopEnabled = false, isInTimerSetMode = true)
    )
    val enabledButtons: State<TimerActionButtonsState> = _enabledButtons

    private val _currentSelectedTimerValue = mutableStateOf(TimerValue.Minutes)
    val currentSelectedTimerValue: State<TimerValue> = _currentSelectedTimerValue

    private val _timer = mutableStateOf(Timer())
    val timer: State<Timer> = _timer

    private val _timerProgress = mutableStateOf(0.0)
    val timerProgress: State<Double> = _timerProgress

    private lateinit var countDownTimer: CountDownTimer

    private val timerValueScaleFactor = mutableStateOf(6.0)

    private val _sliderValue = mutableStateOf(0.0)
    val sliderValue: State<Double> = _sliderValue

    private val _hours = mutableStateOf<Long>(0)
    private val hours: State<Long> = _hours

    private val _minutes = mutableStateOf<Long>(0)
    private val minutes: State<Long> = _minutes

    private val _seconds = mutableStateOf<Long>(0)
    private val seconds: State<Long> = _seconds

    fun onEvent(timerEvent: TimerEvent) {
        when(timerEvent) {
            is TimerEvent.StartTimer -> {
                if(enabledButtons.value.isPlayEnabled) {
                    _timer.value = timer.value.copy(timerState = TimerState.Running)
                    startTimer()
                    updateButtons()
                }
            }
            is TimerEvent.PauseTimer -> {
                countDownTimer.cancel()
                _timer.value = timer.value.copy(timerState = TimerState.Paused)
                updateButtons()
            }
            is TimerEvent.StopTimer -> {
                countDownTimer.cancel()
                onTimerFinished()
            }
            is TimerEvent.TimerValueClicked -> {
                if(currentSelectedTimerValue.value != timerEvent.timerValue) {
                    _currentSelectedTimerValue.value = timerEvent.timerValue

                    when(timerEvent.timerValue) {
                        TimerValue.Hours -> {
                            timerValueScaleFactor.value = TimerValueScaleFactor.HourScaleFactor.scaleFactor
                            _sliderValue.value = timer.value.hours * timerValueScaleFactor.value
                        }
                        TimerValue.Minutes -> {
                            timerValueScaleFactor.value = TimerValueScaleFactor.MinuteAndSecondScaleFactor.scaleFactor
                            _sliderValue.value = timer.value.minutes * timerValueScaleFactor.value
                        }
                        TimerValue.Seconds -> {
                            timerValueScaleFactor.value = TimerValueScaleFactor.MinuteAndSecondScaleFactor.scaleFactor
                            _sliderValue.value = timer.value.seconds * timerValueScaleFactor.value
                        }
                    }
                }
            }
            is TimerEvent.SelectedTimerValueChanged -> {
                _sliderValue.value = timerEvent.sliderValue
                val timerValue = (timerEvent.sliderValue / timerValueScaleFactor.value).toLong()
                when(currentSelectedTimerValue.value) {
                    TimerValue.Hours -> {
                        _hours.value = timerValue
                    }
                    TimerValue.Minutes -> {
                        _minutes.value = timerValue
                    }
                    TimerValue.Seconds -> {
                        _seconds.value = timerValue
                    }
                }
                val totalTime = (hours.value * 3600) + (minutes.value * 60) + (seconds.value)
                _timer.value = timer.value.copy(totalTimeInSeconds = totalTime, secondsRemaining = totalTime)
            }
            is TimerEvent.SaveTimer -> {
                viewModelScope.launch {
                    timerDatastore.setTimer(timer.value)
                }
            }
            is TimerEvent.InitTimer -> {
                viewModelScope.launch {
                    val savedTimer = timerDatastore.getTimer().firstOrNull()
                    savedTimer?.let {
                        _timer.value = it

                        if(it.timerState == TimerState.Running) {
                            onEvent(TimerEvent.StartTimer)
                        }
                    }
                }
            }
        }
    }

    private fun updateButtons() {
        when(timer.value.timerState) {
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
        countDownTimer = object : CountDownTimer(timer.value.secondsRemaining * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                _timer.value = timer.value.copy(secondsRemaining = secondsRemaining)
                _timerProgress.value =
                    (1.0 - ((timer.value.totalTimeInSeconds - secondsRemaining).toDouble() / timer.value.totalTimeInSeconds.toDouble())) * 360.0
            }

            override fun onFinish() {
                onTimerFinished()
            }
        }.start()
    }

    private fun onTimerFinished() {
        _timer.value = Timer()
        _sliderValue.value = 0.0
        updateButtons()
    }

}