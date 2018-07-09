package com.arthurnagy.workoutlog.feature.workouts

import androidx.lifecycle.ViewModel
import com.arthurnagy.workoutlog.core.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class WorkoutsModule {

    @ContributesAndroidInjector
    abstract fun bindWorkoutsFragment(): WorkoutsFragment

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutsViewModel::class)
    abstract fun bindWorkoutsViewModel(workoutsViewModel: WorkoutsViewModel): ViewModel

}