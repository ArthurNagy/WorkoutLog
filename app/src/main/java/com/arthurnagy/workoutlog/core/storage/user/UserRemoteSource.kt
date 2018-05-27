package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.coroutines.await
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.Result
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRemoteService @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun createUser(authenticationCredential: AuthCredential): Result<User> {
        try {
            val authResult = firebaseAuth.signInWithCredential(authenticationCredential).await()
            TODO()
        } catch (exception: Exception) {
            return Result.Error(exception)
        }
    }

}