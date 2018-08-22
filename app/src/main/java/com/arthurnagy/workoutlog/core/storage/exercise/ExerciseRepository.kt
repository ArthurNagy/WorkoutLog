package com.arthurnagy.workoutlog.core.storage.exercise

import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.storage.DataRepository

class ExerciseRepository(
    exerciseRemoteSource: ExerciseRemoteSource,
    exerciseMemorySource: ExerciseMemorySource
) : DataRepository<Int, Exercise>(exerciseRemoteSource, exerciseMemorySource) {

    override fun cacheData(data: Exercise) {
        memorySource.cache(data.id, data)
    }

}

