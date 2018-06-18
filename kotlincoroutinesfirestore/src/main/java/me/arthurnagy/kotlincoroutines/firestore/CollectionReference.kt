package me.arthurnagy.kotlincoroutines.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Source
import kotlin.coroutines.experimental.suspendCoroutine

//region GET
suspend inline fun <reified T> CollectionReference.getList(source: Source = Source.DEFAULT): List<T> = this.get(source).getList()

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