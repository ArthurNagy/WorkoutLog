package com.arthurnagy.workoutlog.feature.workout

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val workoutModule = module {
    viewModel { WorkoutViewModel(get()) }
}