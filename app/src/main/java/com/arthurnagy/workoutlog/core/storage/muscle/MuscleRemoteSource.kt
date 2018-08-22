package com.arthurnagy.workoutlog.core.storage.muscle

import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.storage.DataSource
import com.google.firebase.firestore.CollectionReference
import me.arthurnagy.kotlincoroutines.Result
import me.arthurnagy.kotlincoroutines.awaitGetResult

class MuscleRemoteSource(
    private val muscleReference: CollectionReference
) : DataSource<Int, GenericData> {

    override suspend fun get(id: Int): Result<GenericData> = muscleReference.document(id.toString()).awaitGetResult()

    override suspend fun get(): Result<List<GenericData>> = muscleReference.awaitGetResult()

}