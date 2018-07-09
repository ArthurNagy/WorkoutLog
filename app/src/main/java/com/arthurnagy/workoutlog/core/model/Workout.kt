package com.arthurnagy.workoutlog.core.model

data class Workout(
    val id: Int,
    val dayId: Int,
    val timestamp: Long,
    val exercises: List<WorkoutExercise>
//TODO: add necessary fields
) {
    companion object {
        const val REFERENCE = "workouts"
    }

}