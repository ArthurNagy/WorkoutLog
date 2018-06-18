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
private suspend fun <T> getDocumentValue(document: DocumentReference, type: Class<T>, source: Source = Source.DEFAULT): T =
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
suspend fun <T> DocumentReference.getValue(type: Class<T>, source: Source = Source.DEFAULT): T = getDocumentValue(this, type, source)

/**
 * @param source
 * @return
 * @receiver
 */
suspend inline fun <reified T> DocumentReference.getValue(source: Source = Source.DEFAULT): T = this.getValue(T::class.java, source)


suspend inline fun <reified T> DocumentReference.getValueResult(source: Source = Source.DEFAULT): Result<T> = wrapIntoResult { this.getValue<T>(source) }
//endregion

//region SET
/**
 *
 */
suspend fun <T : Any> DocumentReference.setValue(data: T, setOptions: SetOptions? = null): Unit = suspendCoroutine { continuation ->
    (if (setOptions == null) this.set(data) else this.set(data, setOptions)).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to set $data to document $this"))
        }
    }
}

suspend fun <T : Any> DocumentReference.setValueResult(data: T, setOptions: SetOptions? = null): Result<Unit> =
    wrapIntoResult { this.setValue(data, setOptions) }

/**
 *
 */
suspend fun DocumentReference.setMap(data: Map<String, Any>, setOptions: SetOptions? = null) = setValue(data, setOptions)

suspend fun DocumentReference.setMapResult(data: Map<String, Any>, setOptions: SetOptions? = null): Result<Unit> =
    wrapIntoResult { this.setMap(data, setOptions) }
//endregion

//region UPDATE
/**
 *
 */
suspend fun DocumentReference.updateValue(data: Map<String, Any>): Unit = suspendCoroutine { continuation ->
    this.update(data).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to update document $this with value $data"))
        }
    }
}

suspend fun DocumentReference.updateValueResult(data: Map<String, Any>): Result<Unit> = wrapIntoResult { this.updateValue(data) }

/**
 *
 */
suspend fun DocumentReference.updateValue(vararg fields: Pair<String, Any>) = updateValue(fields.toMap())

suspend fun DocumentReference.updateValueResult(vararg fields: Pair<String, Any>) = updateValueResult(fields.toMap())
//endregion

//region DELETE
/**
 *
 */
suspend fun DocumentReference.deleteValue(): Unit = suspendCoroutine { continuation ->
    this.delete().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to delete document $this"))
        }
    }
}

suspend fun DocumentReference.deleteValueResult(): Result<Unit> = wrapIntoResult { this.deleteValue() }
//endregion