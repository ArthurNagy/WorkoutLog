package com.arthurnagy.workoutlog.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenericData(val id: Int, val name: String) : Parcelable {
    companion object {
        const val CATEGORY_REFERENCE = "categories"
        const val EQUIPMENT_REFERENCE = "equipments"
        const val MUSCLE_REFERENCE = "muscles"

    }
}