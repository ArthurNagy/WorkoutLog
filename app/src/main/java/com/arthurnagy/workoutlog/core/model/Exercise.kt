package com.arthurnagy.workoutlog.core.model

data class Exercise(
    val id: Int = 0,
    val categoryId: Int = 0,
    val equipmentIds: List<Int>? = null,
    val muscleIds: List<Int>? = null,
    val secondaryMuscleIds: List<Int>? = null,
    val name: String? = ""
) {
    companion object {
        const val REFERENCE = "exercises"
    }
}