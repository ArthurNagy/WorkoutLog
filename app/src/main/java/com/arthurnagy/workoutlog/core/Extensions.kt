package com.arthurnagy.workoutlog.core

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

inline fun <reified T> DocumentSnapshot.serialize(): T = toObject(T::class.java) ?: throw Exception("Couldn't serialize $this to ${T::class.java}")

inline fun <reified T> QuerySnapshot.serialize(): List<T> = toObjects(T::class.java)

suspend fun <T> Task<T>.awaitResult(): Result<T> = try {
    Result.Success(this.await())
} catch (exception: Exception) {
    Result.Error(exception)
}


