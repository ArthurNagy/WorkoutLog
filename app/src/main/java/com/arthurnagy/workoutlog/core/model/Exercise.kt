package com.arthurnagy.workoutlog.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Exercise(
    val id: Int,
    val categoryId: Int,
    val equipmentIds: List<Int>?,
    val muscleIds: List<Int>,
    val secondaryMuscleIds: List<Int>?,
    val name: String
) : Parcelable {
    companion object {
        const val REFERENCE = "exercise"
    }
}