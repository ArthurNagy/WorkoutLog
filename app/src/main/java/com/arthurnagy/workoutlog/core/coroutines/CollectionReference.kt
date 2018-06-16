/*
 * Copyright (c) 2018 Halcyon Mobile
 * http://www.halcyonmobile.com
 * All rights reserved.
 */

package com.arthurnagy.workoutlog.core.coroutines

import com.google.firebase.firestore.CollectionReference
import kotlin.coroutines.experimental.suspendCoroutine

private suspend fun <T : Any> readCollectionReference(collection: CollectionReference, type: Class<T>): List<T> = suspendCoroutine { continuation ->
    collection.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            try {
                val data: List<T> = task.result.toObjects(type)
                continuation.resume(data)
            } catch (exception: Exception) {
                continuation.resumeWithException(exception)
            }
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to read collection from $collection of type: $type"))
        }
    }
}

suspend fun <T : Any> CollectionReference.readList(type: Class<T>): List<T> = readCollectionReference(this, type)

suspend inline fun <reified T : Any> CollectionReference.readList(): List<T> = readList(T::class.java)