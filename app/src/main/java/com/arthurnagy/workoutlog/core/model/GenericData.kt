package com.arthurnagy.workoutlog.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenericData(val id: Int, val name: String) : Parcelable {
    companion object {
        const val CATEGORY_REFERENCE = "category"
        const val EQUIPMENT_REFERENCE = "equipment"
        const val MUSCLE_REFERENCE = "muscle"

    }
}