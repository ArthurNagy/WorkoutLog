package com.arthurnagy.workoutlog.core

sealed class Result<out T> {
    class Success<out T>(val value: T) : Result<T>()
    class Error(val exception: Throwable) : Result<Nothing>()
}

inline fun <T, R> Result<T>.map(crossinline transform: (T) -> R): Result<R> = when (this) {
    is Result.Success<T> -> try {
        Result.Success(transform(this.value))
    } catch (exception: Exception) {
        Result.Error(exception)
    }
    is Result.Error -> this
}