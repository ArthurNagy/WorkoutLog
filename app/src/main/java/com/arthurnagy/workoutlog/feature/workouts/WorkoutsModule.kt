package com.arthurnagy.workoutlog.feature.workouts

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val workoutsModule = module {

    viewModel { WorkoutsViewModel(get()) }
}