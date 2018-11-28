package com.arthurnagy.workoutlog.feature.profile

import com.arthurnagy.workoutlog.core.AppDispatchers
import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.WorkoutLogViewModel
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileViewModel(dispatchers: AppDispatchers, private val userRepository: UserRepository) : WorkoutLogViewModel(dispatchers) {

    init {
        launch {
            val userResult = withContext(dispatchers.io) { userRepository.getUser() }
            when (userResult) {
                is Result.Success -> println("UserProfileViewModel: user: ${userResult.value}")
                is Result.Error -> println("UserProfileViewModel: error: ${userResult.exception}")
            }
        }
    }

}