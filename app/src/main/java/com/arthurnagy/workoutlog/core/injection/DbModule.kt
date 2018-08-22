package com.arthurnagy.workoutlog.core.injection

import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.model.Routine
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.model.Workout
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module.module

val dbModule = module {

    single { FirebaseFirestore.getInstance() }

    factory(User.REFERENCE) { get<FirebaseFirestore>().collection(User.REFERENCE) }

    factory(Workout.REFERENCE) { get<FirebaseFirestore>().collection(Workout.REFERENCE) }

    factory(Routine.REFERENCE) { get<FirebaseFirestore>().collection(Routine.REFERENCE) }

    factory(Exercise.REFERENCE) { get<FirebaseFirestore>().collection(Exercise.REFERENCE) }

    factory(GenericData.CATEGORY_REFERENCE) { get<FirebaseFirestore>().collection(GenericData.CATEGORY_REFERENCE) }

    factory(GenericData.EQUIPMENT_REFERENCE) { get<FirebaseFirestore>().collection(GenericData.EQUIPMENT_REFERENCE) }

    factory(GenericData.MUSCLE_REFERENCE) { get<FirebaseFirestore>().collection(GenericData.MUSCLE_REFERENCE) }

}