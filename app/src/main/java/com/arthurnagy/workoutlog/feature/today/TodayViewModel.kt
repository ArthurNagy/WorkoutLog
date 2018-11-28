package com.arthurnagy.workoutlog.feature.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arthurnagy.workoutlog.core.AppDispatchers
import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.WorkoutLogViewModel
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class TodayViewModel(dispatchers: AppDispatchers, private val userRepository: UserRepository) : WorkoutLogViewModel(dispatchers) {

    private val calendar = Calendar.getInstance()
    private val _events = MutableLiveData<Event>()
    val event: LiveData<Event> = _events

    init {

    }

    fun userProfileSelected() {
        launch {
            val userResult = withContext(dispatchers.io) { userRepository.getUser() }
            when (userResult) {
                is Result.Success -> _events.value = Event.Profile(userResult.value)
                is Result.Error -> _events.value = Event.SignIn
            }
        }
    }

    fun userSignedIn() {
        launch {
            val createUserResult = withContext(dispatchers.io) { userRepository.createUser() }
            when (createUserResult) {
                is Result.Success -> _events.value = Event.Profile(createUserResult.value)
                is Result.Error -> _events.value = Event.CreateUserError
            }
        }
    }

    sealed class Event {
        data class Profile(val user: User) : Event()
        object SignIn : Event()
        object CreateUserError : Event()
    }

}