package com.arthurnagy.workoutlog.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

open class WorkoutLogViewModel : ViewModel() {

    private val job = Job()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun launchWithParent(
        context: CoroutineContext = DefaultDispatcher,
        block: suspend CoroutineScope.() -> Unit
    ) = launch(context = context, parent = job, block = block)

}