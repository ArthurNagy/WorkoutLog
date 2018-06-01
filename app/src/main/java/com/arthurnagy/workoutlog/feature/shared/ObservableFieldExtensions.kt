package com.arthurnagy.workoutlog.feature.shared

import androidx.databinding.Observable
import androidx.databinding.ObservableField

inline fun <R> ObservableField<R>.observe(crossinline observer: (R?) -> Unit): Observable.OnPropertyChangedCallback =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(p0: Observable?, p1: Int) {
            observer(get())
        }
    }.also { this.addOnPropertyChangedCallback(it) }

inline fun <T, R> ObservableField<R>.dependsOn(dependableField: ObservableField<T>, crossinline mapper: (T?) -> R): ObservableField<R> {
    dependableField.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            set(mapper(dependableField.get()))
        }
    })
    return this
}