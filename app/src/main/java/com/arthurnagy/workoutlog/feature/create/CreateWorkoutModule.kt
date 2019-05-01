package com.arthurnagy.workoutlog.feature.create

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val createWorkoutModule = module {
    viewModel { CreateWorkoutViewModel(get()) }
}