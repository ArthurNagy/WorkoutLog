package com.arthurnagy.workoutlog.core.coroutines

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
private suspend fun <T : Any> getValue(document: DocumentReference, type: Class<T>, source: Source = Source.DEFAULT): T =
    suspendCoroutine { continuation ->
        document.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.toObject(type)?.let { data ->
                    continuation.resume(data)
                } ?: continuation.resumeWithException(Exception("Failed to get document from $document of type: $type"))
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
suspend fun <T : Any> DocumentReference.get(type: Class<T>, source: Source = Source.DEFAULT): T = getValue(this, type, source)

/**
 * @param source
 * @return
 * @receiver
 */
suspend inline fun <reified T : Any> DocumentReference.getValue(source: Source = Source.DEFAULT): T = this.get(T::class.java, source)
//endregion

//region SET

/**
 * @param
 */
private suspend fun <T : Any> setValue(document: DocumentReference, data: T, setOptions: SetOptions? = null): Unit = suspendCoroutine { continuation ->
    if (setOptions == null) document.set(data) else document.set(data, setOptions).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to set $data to document $document"))
        }
    }
}

/**
 *
 */
suspend fun <T : Any> DocumentReference.set(data: T, setOptions: SetOptions? = null) = setValue(this, data)

/**
 *
 */
suspend fun DocumentReference.setMap(data: Map<String, Any>, setOptions: SetOptions? = null) = set(data, setOptions)
//endregion

//region UPDATE
/**
 *
 */
private suspend fun updateValue(document: DocumentReference, data: Map<String, Any>): Unit = suspendCoroutine { continuation ->
    document.update(data).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to update document $document with value $data"))
        }
    }
}

/**
 *
 */
suspend fun DocumentReference.update(data: Map<String, Any>) = updateValue(this, data)

/**
 *
 */
suspend fun DocumentReference.update(vararg fields: Pair<String, Any>) = update(fields.toMap())
//endregion

//region DELETE
/**
 *
 */
private suspend fun deleteValue(document: DocumentReference): Unit = suspendCoroutine { continuation ->
    document.delete().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(Unit)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to delete document $document"))
        }
    }
}

/**
 *
 */
suspend fun DocumentReference.delete() = deleteValue(this)
//endregion