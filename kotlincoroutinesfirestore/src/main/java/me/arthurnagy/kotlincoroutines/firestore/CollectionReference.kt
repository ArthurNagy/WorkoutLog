package me.arthurnagy.kotlincoroutines.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Source
import kotlin.coroutines.experimental.suspendCoroutine

//region GET
private suspend fun <T> getCollectionReference(collection: CollectionReference, type: Class<T>, source: Source = Source.DEFAULT): List<T> =
    suspendCoroutine { continuation ->
        collection.get(source).addOnCompleteListener { task ->
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

suspend fun <T> CollectionReference.getList(type: Class<T>, source: Source = Source.DEFAULT): List<T> = getCollectionReference(this, type, source)

suspend inline fun <reified T> CollectionReference.getList(source: Source = Source.DEFAULT): List<T> = getList(T::class.java, source)

suspend inline fun <reified T> CollectionReference.getListResult(source: Source = Source.DEFAULT): Result<List<T>> = wrapIntoResult { this.getList<T>(source) }
//endregion

//region ADD
suspend fun <T : Any> CollectionReference.addDocument(data: T): DocumentReference = suspendCoroutine { continuation ->
    this.add(data).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to add $data to collection: $this"))
        }
    }
}

suspend fun <T : Any> CollectionReference.addDocumentResult(data: T): Result<DocumentReference> = wrapIntoResult { this.addDocument(data) }
//endregion