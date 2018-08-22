package com.arthurnagy.workoutlog.core.storage.equipment

import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.storage.DataRepository

class EquipmentRepository(
    remoteSource: EquipmentRemoteSource,
    memorySource: EquipmentMemorySource
) : DataRepository<Int, GenericData>(remoteSource, memorySource) {

    override fun cacheData(data: GenericData) {
        memorySource.cache(data.id, data)
    }
}