package com.danielomeara.clock.features.timer.di

import android.content.Context
import com.danielomeara.clock.features.timer.data.datastore.TimerDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object TimerModule {

    @Provides
    @ActivityRetainedScoped
    fun providesTimerDatastore(@ApplicationContext appContext: Context): TimerDatastore {
        return TimerDatastore(appContext)
    }

}