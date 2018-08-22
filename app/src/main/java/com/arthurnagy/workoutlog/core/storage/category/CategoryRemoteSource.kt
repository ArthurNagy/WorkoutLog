package com.arthurnagy.workoutlog.core.storage.category

import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.storage.DataSource
import com.google.firebase.firestore.CollectionReference
import me.arthurnagy.kotlincoroutines.Result
import me.arthurnagy.kotlincoroutines.awaitGetResult

class CategoryRemoteSource(
    private val cateogryReference: CollectionReference
) : DataSource<Int, GenericData> {

    override suspend fun get(id: Int): Result<GenericData> = cateogryReference.document(id.toString()).awaitGetResult()

    override suspend fun get(): Result<List<GenericData>> = cateogryReference.awaitGetResult()
}