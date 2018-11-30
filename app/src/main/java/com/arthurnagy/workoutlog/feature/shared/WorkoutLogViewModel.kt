package com.arthurnagy.workoutlog.feature.shared

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class WorkoutLogViewModel(protected val dispatchers: AppDispatchers) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = dispatchers.main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}