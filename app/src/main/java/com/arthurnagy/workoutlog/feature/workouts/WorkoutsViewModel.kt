package com.arthurnagy.workoutlog.feature.workouts

import com.arthurnagy.workoutlog.core.WorkoutLogViewModel
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import javax.inject.Inject

class WorkoutsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : WorkoutLogViewModel()