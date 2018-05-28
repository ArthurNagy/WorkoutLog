package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.coroutines.await
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.Result
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.Reusable
import javax.inject.Inject

@Reusable
class UserRemoteSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun createUser(authenticationCredential: AuthCredential): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithCredential(authenticationCredential).await()
            val firebaseUser = firebaseAuth.currentUser ?: authResult.user
            firebaseUser?.let {
                val user = User(it.uid, it.photoUrl.toString())
                // TODO: add more fields to user and save to database
                Result.Success(user)
            } ?: Result.Error(Exception("Couldn't create user"))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

}