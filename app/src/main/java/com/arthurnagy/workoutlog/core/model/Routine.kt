package com.arthurnagy.workoutlog.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Routine(
    val id: Int
//TODO: add necessary fields
) : Parcelable {
    companion object {
        const val REFERENCE = "routines"
    }
}