/*
 * Copyright (c) 2018 Halcyon Mobile
 * http://www.halcyonmobile.com
 * All rights reserved.
 */

package com.arthurnagy.workoutlog.core.coroutines

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Source
import kotlin.coroutines.experimental.suspendCoroutine

private suspend fun <T : Any> getDocumentValue(document: DocumentReference, type: Class<T>, source: Source = Source.DEFAULT): T =
    suspendCoroutine { continuation ->
        document.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.toObject(type)?.let { data ->
                    continuation.resume(data)
                } ?: continuation.resumeWithException(Exception("Failed to read document from $document of type: $type"))
            } else {
                continuation.resumeWithException(task.exception ?: Exception("Failed to read document from $document of type: $type"))
            }
        }
    }

suspend fun <T : Any> DocumentReference.getValue(type: Class<T>, source: Source = Source.DEFAULT): T = getDocumentValue(this, type, source)

suspend inline fun <reified T : Any> DocumentReference.getValue(source: Source = Source.DEFAULT): T = getValue(T::class.java, source)

private suspend fun <T : Any> setDocumentValue(document: DocumentReference, data: T): Unit = suspendCoroutine { continuation ->
    document.set(data).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to write $data to document $document"))
        }
    }
}

suspend fun <T : Any> DocumentReference.setValue(data: T) = setDocumentValue(this, data)
