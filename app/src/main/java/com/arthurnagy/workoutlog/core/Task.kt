package com.arthurnagy.workoutlog.core

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.tasks.await

suspend fun <T> Task<T>.awaitResult(): Result<T> = try {
    Result.Success(this.await())
} catch (exception: Exception) {
    Result.Error(exception)
}