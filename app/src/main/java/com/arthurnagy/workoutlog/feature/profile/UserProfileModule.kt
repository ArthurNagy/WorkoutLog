package com.arthurnagy.workoutlog.feature.profile

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userProfileModule = module {
    viewModel { UserProfileViewModel(get(), get()) }
}