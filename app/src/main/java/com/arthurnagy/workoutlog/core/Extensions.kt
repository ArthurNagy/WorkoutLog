package com.arthurnagy.workoutlog.core

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.util.Calendar

inline fun consumeOptionsItemSelected(block: () -> Unit): Boolean {
    block()
    return true
}

fun Fragment.requireAppCompatActivity() = this.requireActivity() as AppCompatActivity

fun ViewDataBinding.showSnackbar(@StringRes message: Int) = Snackbar.make(this.root, message, Snackbar.LENGTH_LONG).show()

inline fun <reified T> DocumentSnapshot.serialize(): T = toObject(T::class.java) ?: throw Exception("Couldn't serialize $this to ${T::class.java}")

inline fun <reified T> QuerySnapshot.serialize(): List<T> = toObjects(T::class.java)

suspend fun <T> Task<T>.awaitResult(): Result<T> = try {
    Result.Success(this.await())
} catch (exception: Exception) {
    Result.Error(exception)
}

fun Calendar.dayOfWeek() = this.get(Calendar.DAY_OF_WEEK)

fun Calendar.dayOfYear() = this.get(Calendar.DAY_OF_YEAR)

fun Calendar.dayOfMonth() = this.get(Calendar.DAY_OF_MONTH)

fun Calendar.year() = this.get(Calendar.YEAR)

fun Calendar.month() = this.get(Calendar.MONTH)


