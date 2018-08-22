package com.arthurnagy.workoutlog.core.storage.exercise

import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.storage.MemoryDataSource

class ExerciseMemorySource : MemoryDataSource<Int, Exercise>()