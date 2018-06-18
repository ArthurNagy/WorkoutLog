package me.arthurnagy.kotlincoroutines.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Coroutine support to Firebase Task interface
 *
 * This extension function allows you to interact with a Firebase [com.google.android.gms.tasks.Task]
 * using the `await()` method instead of the standard listeners.
 *
 * There is a sample code below comparing the two approaches. Assuming that
 * `auth` variable has the value returned from `FirebaseAuth.getInstance()`
 * method call then your code can be something like:
 *
 * ```
 * auth.getUserByEmail(email)
 *   .addOnSuccessListener { user -> println(user) }
 *   .addOnFailureListener { exception -> println(exception) }
 * ```
 *
 * When using the coroutine approach, it should be more like this:
 *
 * ```
 * try {
 *   val user = auth.getUserByEmail(email).await()
 *   println(user)
 * } catch (exception: Exception) {
 *   println(exception)
 * }
 * ```
 *
 * @param T The type of the value been returned
 * @throws Exception Thrown in case of network error or other reasons described in the Firebase docs
 * @return The value returned by the Firebase success callback
 */
suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
    this.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result)
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Firebase Task failed to execute"))
        }
    }
}

suspend fun <T> Task<T>.awaitResult(): Result<T> = wrapIntoResult { this.await() }

private suspend fun <T> awaitTaskQueryList(task: Task<QuerySnapshot>, type: Class<T>): List<T> = suspendCoroutine { continuation ->
    task.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            try {
                val data: List<T> = task.result.toObjects(type)
                continuation.resume(data)
            } catch (exception: Exception) {
                continuation.resumeWithException(exception)
            }
        } else {
            continuation.resumeWithException(task.exception ?: Exception("Failed to read task list: $task of type: $type"))
        }
    }
}

suspend fun <T> Task<QuerySnapshot>.awaitList(type: Class<T>): List<T> = awaitTaskQueryList(this, type)

suspend inline fun <reified T> Task<QuerySnapshot>.awaitList(): List<T> = awaitList(T::class.java)

suspend inline fun <reified T> Task<QuerySnapshot>.awaitListResult(): Result<List<T>> = wrapIntoResult { this.awaitList<T>() }