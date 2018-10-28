package com.arthurnagy.workoutlog.core

sealed class Result<out T> {
    class Success<out T>(val value: T) : Result<T>()
    class Error(val exception: Throwable) : Result<Nothing>()
}

inline fun <T, R> Result<T>.map(crossinline transform: (T) -> R): Result<R> = when (this) {
    is Result.Success<T> -> Result.Success(transform(this.value))
    is Result.Error -> this
}