package com.arthurnagy.workoutlog.feature.account

import com.arthurnagy.workoutlog.core.WorkoutLogViewModel
import com.arthurnagy.workoutlog.core.storage.Result
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class AccountMenuViewModel @Inject constructor(private val userRepository: UserRepository) : WorkoutLogViewModel() {

    fun signIn(authCredential: AuthCredential) {
        launch(UI) {
            val result = userRepository.createUser(authCredential)
            when (result) {
                is Result.Success -> result.data
                is Result.Error -> result.exception
            }
        }
    }

}