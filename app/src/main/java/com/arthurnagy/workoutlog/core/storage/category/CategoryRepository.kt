package com.arthurnagy.workoutlog.core.storage.category

import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.storage.DataRepository

class CategoryRepository(
    remoteSource: CategoryRemoteSource,
    memorySource: CategoryMemorySource
) : DataRepository<Int, GenericData>(remoteSource, memorySource) {

    override fun cacheData(data: GenericData) {
        memorySource.cache(data.id, data)
    }
}