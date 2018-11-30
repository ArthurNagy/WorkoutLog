package com.arthurnagy.workoutlog.core.model

data class WorkoutExercise(
    val id: Int,
    val exercise: Exercise,
    val sets: List<Set>
)