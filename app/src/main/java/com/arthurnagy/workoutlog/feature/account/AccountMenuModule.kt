package com.arthurnagy.workoutlog.feature.account

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val accountMenuModule = module {

    viewModel<AccountMenuViewModel>()
}