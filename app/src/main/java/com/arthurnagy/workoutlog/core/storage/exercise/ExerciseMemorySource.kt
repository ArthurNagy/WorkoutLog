package com.arthurnagy.workoutlog.core.storage.exercise

import com.arthurnagy.workoutlog.core.injection.AppScope
import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.storage.MemoryDataSource
import javax.inject.Inject

@AppScope
class ExerciseMemorySource @Inject constructor() : MemoryDataSource<Int, Exercise>()