package com.danielomeara.clock.features.timer.di

import com.danielomeara.clock.features.timer.domain.usecases.GenerateTimerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object TimerModule {

    @Provides
    @ActivityRetainedScoped
    fun providesGenerateTimerTextUseCase(): GenerateTimerUseCase {
        return GenerateTimerUseCase()
    }

}