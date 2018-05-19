package com.arthurnagy.workoutlog.core.injection

import android.content.Context
import com.arthurnagy.workoutlog.WorkoutLogApp
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @JvmStatic
    @Provides
    @AppContext
    fun provideAppContext(workoutLogApp: WorkoutLogApp): Context = workoutLogApp

}