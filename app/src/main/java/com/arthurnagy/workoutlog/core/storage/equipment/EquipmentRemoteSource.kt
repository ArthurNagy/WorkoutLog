package com.arthurnagy.workoutlog.core.storage.equipment

import com.arthurnagy.workoutlog.core.Result
import com.arthurnagy.workoutlog.core.awaitResult
import com.arthurnagy.workoutlog.core.map
import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.serialize
import com.arthurnagy.workoutlog.core.storage.DataSource
import com.google.firebase.firestore.CollectionReference

class EquipmentRemoteSource(
    private val equipmentReference: CollectionReference
) : DataSource<Int, GenericData> {

    override suspend fun get(id: Int): Result<GenericData> = equipmentReference.document(id.toString()).get().awaitResult()
        .map { it.serialize<GenericData>() }

    override suspend fun get(): Result<List<GenericData>> = equipmentReference.get().awaitResult()
        .map { it.serialize<GenericData>() }
}