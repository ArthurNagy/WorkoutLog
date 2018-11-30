package com.arthurnagy.workoutlog.core.storage.exercise

import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.awaitResult
import com.arthurnagy.workoutlog.core.map
import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.serialize
import com.arthurnagy.workoutlog.core.storage.DataSource
import com.google.firebase.firestore.CollectionReference

class ExerciseRemoteSource(
    private val exerciseCollection: CollectionReference
) : DataSource<Int, Exercise> {

    override suspend fun get(id: Int): Result<Exercise> = exerciseCollection.document(id.toString()).get().awaitResult()
        .map { it.serialize<Exercise>() }

    override suspend fun get(): Result<List<Exercise>> = exerciseCollection.get().awaitResult().map { it.serialize<Exercise>() }

}