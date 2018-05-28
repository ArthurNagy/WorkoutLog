package com.arthurnagy.workoutlog.core.injection

import androidx.lifecycle.ViewModelProvider
import com.arthurnagy.workoutlog.core.WorkoutLogViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: WorkoutLogViewModelFactory): ViewModelProvider.Factory
}