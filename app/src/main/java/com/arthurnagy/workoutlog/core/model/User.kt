package com.arthurnagy.workoutlog.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String,
    val profilePictureUrl: String
) : Parcelable {
    companion object {
        const val REFERENCE = "user"
    }
}