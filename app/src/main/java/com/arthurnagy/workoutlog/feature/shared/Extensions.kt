package com.arthurnagy.workoutlog.feature.shared

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun consumeOptionsItemSelected(block: () -> Unit): Boolean {
    block()
    return true
}

fun Fragment.requireAppCompatActivity() = this.requireActivity() as AppCompatActivity