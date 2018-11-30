package com.arthurnagy.workoutlog.feature.profile

import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import com.arthurnagy.workoutlog.feature.shared.AppDispatchers
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogViewModel
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