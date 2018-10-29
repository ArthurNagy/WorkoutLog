package com.arthurnagy.workoutlog

import android.app.Application
import com.arthurnagy.workoutlog.core.injection.appModule
import com.arthurnagy.workoutlog.core.injection.dbModule
import com.arthurnagy.workoutlog.core.injection.repositoryModule
import com.arthurnagy.workoutlog.feature.create.createWorkoutModule
import com.arthurnagy.workoutlog.feature.today.todayModule
import org.koin.android.ext.android.startKoin

class WorkoutLogApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, dbModule, repositoryModule, todayModule, createWorkoutModule))
    }

}