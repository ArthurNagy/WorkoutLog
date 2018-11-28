package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.model.User

class UserRepository(
    private val userRemoteSource: UserRemoteSource
) {

    private var user: User? = null

    suspend fun createUser() = userRemoteSource.createUser().also { if (it is Result.Success) user = it.value }

    suspend fun getUser() = user?.let { Result.Success(it) } ?: userRemoteSource.getUser().also { if (it is Result.Success) user = it.value }

}