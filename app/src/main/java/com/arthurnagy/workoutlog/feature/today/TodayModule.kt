package com.arthurnagy.workoutlog.feature.today

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val todayModule = module {
    viewModel { TodayViewModel(get(), get()) }
}