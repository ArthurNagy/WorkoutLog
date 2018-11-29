package com.arthurnagy.workoutlog.feature.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import com.arthurnagy.workoutlog.feature.shared.AppDispatchers
import com.arthurnagy.workoutlog.feature.shared.Event
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TodayViewModel(dispatchers: AppDispatchers, private val userRepository: UserRepository) : WorkoutLogViewModel(dispatchers) {

    private val calendar = Calendar.getInstance()
    private val _events = MutableLiveData<Event<TodayView>>()
    val event: LiveData<Event<TodayView>> = _events

    init {

    }

    fun userProfileSelected() {
        launch {
            val userResult = withContext(dispatchers.io) { userRepository.getUser() }
            when (userResult) {
                is Result.Success -> _events.value = Event(TodayView.Profile(userResult.value))
                is Result.Error -> _events.value = Event(TodayView.SignIn)
            }
        }
    }

    fun userSignedIn() {
        launch {
            val createUserResult = withContext(dispatchers.io) { userRepository.createUser() }
            when (createUserResult) {
                is Result.Success -> _events.value = Event(TodayView.Profile(createUserResult.value))
                is Result.Error -> _events.value = Event(TodayView.CreateUserError)
            }
        }
    }

    sealed class TodayView {
        data class Profile(val user: User) : TodayView()
        object SignIn : TodayView()
        object CreateUserError : TodayView()
    }

}