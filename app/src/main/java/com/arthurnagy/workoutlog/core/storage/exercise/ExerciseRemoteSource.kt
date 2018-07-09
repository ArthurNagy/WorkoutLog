package com.arthurnagy.workoutlog.core.storage.exercise

import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.storage.DataSource
import com.google.firebase.firestore.CollectionReference
import me.arthurnagy.kotlincoroutines.Result
import me.arthurnagy.kotlincoroutines.awaitGetResult
import javax.inject.Inject
import javax.inject.Named

class ExerciseRemoteSource @Inject constructor(
    @Named(Exercise.REFERENCE)
    private val exerciseCollection: CollectionReference
) : DataSource<Int, Exercise> {

    override suspend fun get(id: Int): Result<Exercise> = exerciseCollection.document(id.toString()).awaitGetResult()

    override suspend fun get(): Result<List<Exercise>> = exerciseCollection.awaitGetResult()

}