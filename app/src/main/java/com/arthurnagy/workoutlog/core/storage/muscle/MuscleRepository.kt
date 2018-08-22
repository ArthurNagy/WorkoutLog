package com.arthurnagy.workoutlog.core.storage.muscle

import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.storage.DataRepository

class MuscleRepository(
    muscleRemoteSource: MuscleRemoteSource,
    muscleMemorySource: MuscleMemorySource
) : DataRepository<Int, GenericData>(muscleRemoteSource, muscleMemorySource) {

    override fun cacheData(data: GenericData) {
        memorySource.cache(data.id, data)
    }

}

