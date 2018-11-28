package com.arthurnagy.workoutlog.feature.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.WorkoutLogViewModel
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TodayViewModel(private val userRepository: UserRepository) : WorkoutLogViewModel() {

    private val calendar = Calendar.getInstance()
    private val _events = MutableLiveData<Event>()
    val event: LiveData<Event> = _events

    init {

    }

    fun userProfileSelected() {
        launch {
            val userResult = withContext(Dispatchers.IO) { userRepository.getUser() }
            when (userResult) {
                is Result.Success -> _events.value = Event.ProfileEvent(userResult.value)
                is Result.Error -> _events.value = Event.SignInEvent
            }
        }
    }

    fun userSignedIn(user: User) {

    }

    sealed class Event {
        data class ProfileEvent(val user: User) : Event()
        object SignInEvent : Event()
    }

}