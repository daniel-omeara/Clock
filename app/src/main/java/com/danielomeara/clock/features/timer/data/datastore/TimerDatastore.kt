package com.danielomeara.clock.features.timer.data.datastore

import android.content.Context
import androidx.datastore.dataStore
import com.danielomeara.clock.features.timer.data.datastore.util.TimerSerializer
import com.danielomeara.clock.features.timer.domain.models.Timer
import com.danielomeara.clock.features.timer.presentation.util.TimerState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

val Context.dataStore by dataStore("timer_store.json", TimerSerializer)

class TimerDatastore @Inject constructor(@ApplicationContext appContext: Context) {

    private val timerDataStore = appContext.dataStore

    fun getTimer(): Flow<Timer> {
        return timerDataStore.data
    }

    suspend fun setTimer(timer: Timer) {
        timerDataStore.updateData {
            if(it.timerState == TimerState.Stopped) {
                it.copy(
                    timerState = timer.timerState,
                    totalTimeInSeconds = timer.totalTimeInSeconds,
                    secondsRemaining = timer.secondsRemaining
                )
            } else {
                it.copy(
                    timerState = timer.timerState,
                    secondsRemaining = timer.secondsRemaining
                )
            }
        }
    }

}