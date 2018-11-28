package com.arthurnagy.workoutlog.core.storage.user

import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.awaitResult
import com.arthurnagy.workoutlog.core.map
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.serialize
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference

class UserRemoteSource(
    private val firebaseAuth: FirebaseAuth,
    private val userCollection: CollectionReference
) {

    suspend fun createUser(): Result<User> = firebaseAuth.currentUser?.let { firebaseUser ->
        val user = User(
            id = firebaseUser.uid,
            displayName = firebaseUser.displayName,
            email = firebaseUser.email,
            profilePictureUrl = firebaseUser.photoUrl.toString()
        )
        val userResult = userCollection.document(user.id).set(user).awaitResult()
        when (userResult) {
            is Result.Success -> Result.Success(user)
            is Result.Error -> userResult
        }
    } ?: Result.Error(NullPointerException("Firebase user is null"))

    suspend fun getUser(): Result<User> = firebaseAuth.currentUser?.let { firebaseUser ->
        userCollection.document(firebaseUser.uid).get().awaitResult()
            .map { it.serialize<User>() }
    } ?: Result.Error(Exception("No logged in user"))

}
