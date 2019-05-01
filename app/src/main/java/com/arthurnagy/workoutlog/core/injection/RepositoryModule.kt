package com.arthurnagy.workoutlog.core.injection

import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.storage.category.CategoryMemorySource
import com.arthurnagy.workoutlog.core.storage.category.CategoryRemoteSource
import com.arthurnagy.workoutlog.core.storage.category.CategoryRepository
import com.arthurnagy.workoutlog.core.storage.equipment.EquipmentMemorySource
import com.arthurnagy.workoutlog.core.storage.equipment.EquipmentRemoteSource
import com.arthurnagy.workoutlog.core.storage.equipment.EquipmentRepository
import com.arthurnagy.workoutlog.core.storage.exercise.ExerciseMemorySource
import com.arthurnagy.workoutlog.core.storage.exercise.ExerciseRemoteSource
import com.arthurnagy.workoutlog.core.storage.exercise.ExerciseRepository
import com.arthurnagy.workoutlog.core.storage.muscle.MuscleMemorySource
import com.arthurnagy.workoutlog.core.storage.muscle.MuscleRemoteSource
import com.arthurnagy.workoutlog.core.storage.muscle.MuscleRepository
import com.arthurnagy.workoutlog.core.storage.user.UserRemoteSource
import com.arthurnagy.workoutlog.core.storage.user.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    factory { CategoryRemoteSource(get(named(GenericData.CATEGORY_REFERENCE))) }
    single { CategoryMemorySource() }
    factory { CategoryRepository(get(), get()) }

    factory { EquipmentRemoteSource(get(named(GenericData.EQUIPMENT_REFERENCE))) }
    single { EquipmentMemorySource() }
    factory { EquipmentRepository(get(), get()) }

    factory { ExerciseRemoteSource(get(named(Exercise.REFERENCE))) }
    single { ExerciseMemorySource() }
    factory { ExerciseRepository(get(), get()) }

    factory { MuscleRemoteSource(get(named(GenericData.MUSCLE_REFERENCE))) }
    single { MuscleMemorySource() }
    factory { MuscleRepository(get(), get()) }

    factory { UserRemoteSource(get(), get(named(User.REFERENCE))) }
    single { UserRepository(get()) }

}