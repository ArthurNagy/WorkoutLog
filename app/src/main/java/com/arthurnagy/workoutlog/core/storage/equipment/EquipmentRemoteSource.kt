package com.arthurnagy.workoutlog.core.storage.equipment

import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.storage.DataSource
import com.google.firebase.firestore.CollectionReference
import me.arthurnagy.kotlincoroutines.Result
import me.arthurnagy.kotlincoroutines.awaitGetResult
import javax.inject.Inject
import javax.inject.Named

class EquipmentRemoteSource @Inject constructor(
    @Named(GenericData.EQUIPMENT_REFERENCE)
    private val equipmentReference: CollectionReference
) : DataSource<Int, GenericData> {

    override suspend fun get(id: Int): Result<GenericData> = equipmentReference.document(id.toString()).awaitGetResult()

    override suspend fun get(): Result<List<GenericData>> = equipmentReference.awaitGetResult()
}