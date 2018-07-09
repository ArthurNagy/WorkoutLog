package com.arthurnagy.workoutlog.core.storage.exercise

import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.storage.DataRepository
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    exerciseRemoteSource: ExerciseRemoteSource,
    exerciseMemorySource: ExerciseMemorySource
) : DataRepository<Int, Exercise>(exerciseRemoteSource, exerciseMemorySource) {

    override fun cacheData(data: Exercise) {
        memorySource.cache(data.id, data)
    }

}

