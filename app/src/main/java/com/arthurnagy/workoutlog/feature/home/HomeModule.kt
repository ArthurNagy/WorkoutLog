package com.arthurnagy.workoutlog.feature.home

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @ContributesAndroidInjector()
    abstract fun bindHomeActivity(): HomeActivity

}