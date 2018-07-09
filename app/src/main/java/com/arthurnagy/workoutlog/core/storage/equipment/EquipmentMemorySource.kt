package com.arthurnagy.workoutlog.core.storage.equipment

import com.arthurnagy.workoutlog.core.injection.AppScope
import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.storage.MemoryDataSource
import javax.inject.Inject

@AppScope
class EquipmentMemorySource @Inject constructor() : MemoryDataSource<Int, GenericData>()