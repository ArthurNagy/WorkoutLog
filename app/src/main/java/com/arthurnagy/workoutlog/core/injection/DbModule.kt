package com.arthurnagy.workoutlog.core.injection

import com.arthurnagy.workoutlog.core.model.*
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dbModule = module {

    single { FirebaseFirestore.getInstance() }

    factory(named(User.REFERENCE)) { get<FirebaseFirestore>().collection(User.REFERENCE) }

    factory(named(Workout.REFERENCE)) { get<FirebaseFirestore>().collection(Workout.REFERENCE) }

    factory(named(Routine.REFERENCE)) { get<FirebaseFirestore>().collection(Routine.REFERENCE) }

    factory(named(Exercise.REFERENCE)) { get<FirebaseFirestore>().collection(Exercise.REFERENCE) }

    factory(named(GenericData.CATEGORY_REFERENCE)) { get<FirebaseFirestore>().collection(GenericData.CATEGORY_REFERENCE) }

    factory(named(GenericData.EQUIPMENT_REFERENCE)) { get<FirebaseFirestore>().collection(GenericData.EQUIPMENT_REFERENCE) }

    factory(named(GenericData.MUSCLE_REFERENCE)) { get<FirebaseFirestore>().collection(GenericData.MUSCLE_REFERENCE) }

}