package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.injection.AppScope
import com.arthurnagy.workoutlog.core.model.User
import com.google.firebase.auth.AuthCredential
import me.arthurnagy.kotlincoroutines.Result
import javax.inject.Inject

@AppScope
class UserRepository @Inject constructor(private val userRemoteSource: UserRemoteSource) {

    private var user: User? = null

    suspend fun createUser(authenticationCredential: AuthCredential) =
        userRemoteSource.createUser(authenticationCredential).also { if (it is Result.Success) user = it.value }

    suspend fun getUser() = user?.let { Result.Success(it) } ?: userRemoteSource.getUser().also { if (it is Result.Success) user = it.value }

}