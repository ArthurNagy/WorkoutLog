package com.arthurnagy.workoutlog.feature.shared

import androidx.databinding.Observable
import androidx.databinding.ObservableField

inline fun <T, R> ObservableField<R>.dependsOn(dependableField: ObservableField<T>, crossinline mapper: (T?) -> R): Observable.OnPropertyChangedCallback =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            set(mapper(dependableField.get()))
        }
    }.also { dependableField.addOnPropertyChangedCallback(it) }
