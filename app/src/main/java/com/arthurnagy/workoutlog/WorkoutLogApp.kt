package com.arthurnagy.workoutlog

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.arthurnagy.workoutlog.core.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class WorkoutLogApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Volatile
    private var needsToInject = true


    override fun onCreate() {
        injectApplicationIfNecessary()
        super.onCreate()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    @Inject
    fun setInjected() {
        needsToInject = false
    }

    private fun createApplicationInjector(): AndroidInjector<WorkoutLogApp> = DaggerAppComponent.builder().create(this)

    private fun injectApplicationIfNecessary() {
        if (needsToInject) {
            synchronized(this) {
                if (needsToInject) {
                    createApplicationInjector().inject(this)
                    if (needsToInject) {
                        throw IllegalStateException("Did not inject the Application")
                    }
                }
            }
        }
    }

}