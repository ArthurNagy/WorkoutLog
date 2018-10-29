package com.arthurnagy.workoutlog.core.model

data class Day(
    val id: Int,
    val dayOfWeek: Int,
    val exercises: List<Exercise>
//TODO: add necessary fields
) {
    companion object {
        const val REFERENCE = "days"
    }
}