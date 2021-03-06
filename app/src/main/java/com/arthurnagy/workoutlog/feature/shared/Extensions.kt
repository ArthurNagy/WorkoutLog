package com.arthurnagy.workoutlog.feature.shared

import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.*

inline fun consumeOptionsItemSelected(block: () -> Unit): Boolean {
    block()
    return true
}

fun Fragment.requireAppCompatActivity() = this.requireActivity() as AppCompatActivity

inline fun <reified VB : ViewDataBinding> Fragment.binding(@LayoutRes layoutRes: Int): Lazy<VB> = lazy {
    DataBindingUtil.inflate<VB>(this.layoutInflater, layoutRes, null, false)
}

fun View.showSnackbar(@StringRes message: Int) = Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()

fun Calendar.dayOfWeek() = this.get(Calendar.DAY_OF_WEEK)

fun Calendar.dayOfYear() = this.get(Calendar.DAY_OF_YEAR)

fun Calendar.dayOfMonth() = this.get(Calendar.DAY_OF_MONTH)

fun Calendar.year() = this.get(Calendar.YEAR)

fun Calendar.month() = this.get(Calendar.MONTH)