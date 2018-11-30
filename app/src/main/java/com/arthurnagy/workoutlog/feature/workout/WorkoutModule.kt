package com.arthurnagy.workoutlog.feature.workout

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val workoutModule = module {
    viewModel { WorkoutViewModel(get()) }
}