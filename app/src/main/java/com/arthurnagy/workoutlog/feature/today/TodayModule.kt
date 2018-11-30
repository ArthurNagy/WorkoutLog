package com.arthurnagy.workoutlog.feature.today

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val todayModule = module {
    viewModel { TodayViewModel(get(), get()) }
}