package com.arthurnagy.workoutlog.core.coroutines

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Source
import kotlin.coroutines.experimental.suspendCoroutine

//region GET
private suspend fun <T : Any> getCollectionReference(collection: CollectionReference, type: Class<T>, source: Source = Source.DEFAULT): List<T> =
    suspendCoroutine { continuation ->
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

suspend fun <T : Any> CollectionReference.getList(type: Class<T>, source: Source = Source.DEFAULT): List<T> = getCollectionReference(this, type, source)

suspend inline fun <reified T : Any> CollectionReference.getList(source: Source = Source.DEFAULT): List<T> = getList(T::class.java, source)
//endregion

//region ADD
private suspend fun <T : Any> addDocumentToCollection(collection: CollectionReference, data: T): DocumentReference = suspendCoroutine { continuation ->
    collection.add(data).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to add $data to collection: $collection"))
        }
    }
}

suspend fun <T : Any> CollectionReference.add(data: T): DocumentReference = addDocumentToCollection(this, data)
//endregion