package com.arthurnagy.workoutlog.feature.profile

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val userProfileModule = module {
    viewModel { UserProfileViewModel(get(), get()) }
}