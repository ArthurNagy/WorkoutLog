package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.injection.AppScope
import com.google.firebase.auth.AuthCredential
import javax.inject.Inject

@AppScope
class UserRepository @Inject constructor(private val userRemoteSource: UserRemoteSource) {

    suspend fun createUser(authenticationCredential: AuthCredential) = userRemoteSource.createUser(authenticationCredential)

}