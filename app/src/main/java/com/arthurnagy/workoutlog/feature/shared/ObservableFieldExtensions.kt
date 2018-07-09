package com.arthurnagy.workoutlog.feature.shared

import androidx.databinding.Observable
import androidx.databinding.ObservableField

inline fun <R> ObservableField<R>.observe(crossinline observer: (R?) -> Unit): Observable.OnPropertyChangedCallback =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            observer(get())
        }
    }.also { this.addOnPropertyChangedCallback(it) }

inline fun <reified T> dependantObservabelField(vararg dependencies: Observable, crossinline mapper: () -> T?): ObservableField<T> {
    return object : ObservableField<T>(*dependencies) {
        override fun get(): T? {
            return mapper()
        }
    }
}