package com.arthurnagy.workoutlog.core.injection

import com.arthurnagy.workoutlog.WorkoutLogApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class])
interface AppComponent : AndroidInjector<WorkoutLogApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WorkoutLogApp>()

}