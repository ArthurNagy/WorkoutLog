package com.arthurnagy.workoutlog.feature.create

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val createWorkoutModule = module {
    viewModel { CreateWorkoutViewModel() }
}