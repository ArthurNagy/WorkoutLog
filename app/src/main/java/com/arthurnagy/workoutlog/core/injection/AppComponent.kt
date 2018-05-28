package com.arthurnagy.workoutlog.core.injection

import com.arthurnagy.workoutlog.WorkoutLogApp
import com.arthurnagy.workoutlog.feature.account.AccountMenuModule
import com.arthurnagy.workoutlog.feature.home.HomeModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        HomeModule::class,
        AccountMenuModule::class]
)
interface AppComponent : AndroidInjector<WorkoutLogApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WorkoutLogApp>()

}