package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.coroutines.await
import com.arthurnagy.workoutlog.core.coroutines.getValue
import com.arthurnagy.workoutlog.core.coroutines.setValue
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.Result
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Named

@Reusable
class UserRemoteSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @Named(User.REFERENCE)
    private val userCollection: CollectionReference
) {

    suspend fun createUser(authenticationCredential: AuthCredential): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithCredential(authenticationCredential).await()
            val firebaseUser = firebaseAuth.currentUser ?: authResult.user
            firebaseUser?.let {
                val user = User(
                    id = it.uid,
                    displayName = it.displayName,
                    email = it.email,
                    profilePictureUrl = it.photoUrl.toString()
                )
                userCollection.document(user.id).setValue(user)
                Result.Success(user)
            } ?: Result.Error(Exception("Couldn't create user"))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    suspend fun getUser(): Result<User> {
        return try {
            firebaseAuth.currentUser?.let {
                val user = userCollection.document(it.uid).getValue<User>()
                Result.Success(user)
            } ?: Result.Error(Exception("No logged in user"))

        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

}