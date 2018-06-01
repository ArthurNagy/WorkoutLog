package com.arthurnagy.workoutlog.feature.account

import android.util.Log
import androidx.databinding.ObservableField
import com.arthurnagy.workoutlog.core.WorkoutLogViewModel
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.Result
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import com.arthurnagy.workoutlog.feature.shared.dependsOn
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class AccountMenuViewModel @Inject constructor(private val userRepository: UserRepository) : WorkoutLogViewModel() {

    val user = ObservableField<User>()
    val isUserLoggedIn = ObservableField<Boolean>(false)

    init {
        isUserLoggedIn.dependsOn(user) { it != null }
        launch(UI) {
            val userResult = userRepository.getUser()
            when (userResult) {
                is Result.Success -> user.set(userResult.data)
                is Result.Error -> Log.d("AccountMenuViewModel", "error retrieving user: ${userResult.exception}")
            }
        }
    }

    fun signIn(authCredential: AuthCredential) {
        launch(UI) {
            val result = userRepository.createUser(authCredential)
            when (result) {
                is Result.Success -> Log.d("AccountMenuViewModel", "user: ${result.data}")
                is Result.Error -> Log.d("AccountMenuViewModel", "error: ${result.exception}")
            }
        }
    }

}