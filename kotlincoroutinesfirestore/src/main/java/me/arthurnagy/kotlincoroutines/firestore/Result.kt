package me.arthurnagy.kotlincoroutines.firestore

sealed class Result<out T> {
    class Success<out T>(val value: T) : Result<T>()
    class Error(val exception: Throwable) : Result<Nothing>()
}

inline fun <T> wrapIntoResult(firestoreCoroutine: () -> T): Result<T> = try {
    Result.Success(firestoreCoroutine())
} catch (exception: Exception) {
    Result.Error(exception)
}