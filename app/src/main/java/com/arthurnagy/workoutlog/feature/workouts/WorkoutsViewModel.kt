package com.arthurnagy.workoutlog.feature.workouts

import com.arthurnagy.workoutlog.core.WorkoutLogViewModel
import com.arthurnagy.workoutlog.core.storage.user.UserRepository

class WorkoutsViewModel constructor(
    private val userRepository: UserRepository
) : WorkoutLogViewModel()