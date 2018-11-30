package com.arthurnagy.workoutlog.core.model

data class Routine(
    val id: Int,
    val userId: Int,
    val days: List<Day>
//TODO: add necessary fields
) {
    companion object {
        const val REFERENCE = "routines"
    }
}