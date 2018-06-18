package me.arthurnagy.kotlincoroutines.firestore

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Source
import kotlin.coroutines.experimental.suspendCoroutine

//region GET
/**
 * @param document
 * @param type
 * @param source
 * @return
 */
private suspend fun <T> awaitDocumentValue(document: DocumentReference, type: Class<T>, source: Source = Source.DEFAULT): T =
    suspendCoroutine { continuation ->
        document.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                try {
                    val data: T? = task.result.toObject(type)
                    data?.let { continuation.resume(it) } ?: continuation.resumeWithException(Exception("Failed to get document from $document of type: $type"))
                } catch (exception: Exception) {
                    continuation.resumeWithException(exception)
                }
            } else {
                continuation.resumeWithException(task.exception ?: Exception("Failed to get document from $document of type: $type"))
            }
        }
    }

/**
 * @param type
 * @param source
 * @return
 * @receiver
 */
suspend fun <T> DocumentReference.awaitGet(type: Class<T>, source: Source = Source.DEFAULT): T = awaitDocumentValue(this, type, source)

/**
 * @param source
 * @return
 * @receiver
 */
suspend inline fun <reified T> DocumentReference.awaitGet(source: Source = Source.DEFAULT): T = this.awaitGet(T::class.java, source)


suspend inline fun <reified T> DocumentReference.awaitGetResult(source: Source = Source.DEFAULT): Result<T> = wrapIntoResult { this.awaitGet<T>(source) }
//endregion

//region SET
/**
 *
 */
suspend fun <T : Any> DocumentReference.awaitSet(data: T, setOptions: SetOptions? = null): Unit = suspendCoroutine { continuation ->
    (if (setOptions == null) this.set(data) else this.set(data, setOptions)).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to set $data to document $this"))
        }
    }
}

suspend fun <T : Any> DocumentReference.awaitSetResult(data: T, setOptions: SetOptions? = null): Result<Unit> =
    wrapIntoResult { this.awaitSet(data, setOptions) }

/**
 *
 */
suspend fun DocumentReference.awaitSetMap(data: Map<String, Any>, setOptions: SetOptions? = null) = awaitSet(data, setOptions)

suspend fun DocumentReference.awaitSetMapResult(data: Map<String, Any>, setOptions: SetOptions? = null): Result<Unit> =
    wrapIntoResult { this.awaitSetMap(data, setOptions) }
//endregion

//region UPDATE
/**
 *
 */
suspend fun DocumentReference.awaitUpdate(data: Map<String, Any>): Unit = suspendCoroutine { continuation ->
    this.update(data).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to update document $this with value $data"))
        }
    }
}

suspend fun DocumentReference.awaitUpdateResult(data: Map<String, Any>): Result<Unit> = wrapIntoResult { this.awaitUpdate(data) }

/**
 *
 */
suspend fun DocumentReference.awaitUpdate(vararg fields: Pair<String, Any>) = awaitUpdate(fields.toMap())

suspend fun DocumentReference.awaitUpdateResult(vararg fields: Pair<String, Any>) = awaitUpdateResult(fields.toMap())
//endregion

//region DELETE
/**
 *
 */
suspend fun DocumentReference.awaitDelete(): Unit = suspendCoroutine { continuation ->
    this.delete().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to delete document $this"))
        }
    }
}

suspend fun DocumentReference.awaitDeleteResult(): Result<Unit> = wrapIntoResult { this.awaitDelete() }
//endregion