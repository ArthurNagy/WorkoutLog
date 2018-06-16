package com.arthurnagy.workoutlog.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Workout(
    val id: Int
//TODO: add necessary fields
) : Parcelable {
    companion object {
        const val REFERENCE = "workouts"
    }
}