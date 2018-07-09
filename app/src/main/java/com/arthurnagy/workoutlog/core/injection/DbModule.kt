package com.arthurnagy.workoutlog.core.injection

import com.arthurnagy.workoutlog.core.model.Exercise
import com.arthurnagy.workoutlog.core.model.GenericData
import com.arthurnagy.workoutlog.core.model.Routine
import com.arthurnagy.workoutlog.core.model.User
import com.arthurnagy.workoutlog.core.model.Workout
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Named

@Module
object DbModule {

    @JvmStatic
    @Provides
    @Reusable
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @JvmStatic
    @Provides
    @Reusable
    @Named(User.REFERENCE)
    fun provideUserFirestoreCollection(firestore: FirebaseFirestore): CollectionReference = firestore.collection(User.REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(Workout.REFERENCE)
    fun provideWorkoutFirestoreCollection(firestore: FirebaseFirestore): CollectionReference = firestore.collection(Workout.REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(Routine.REFERENCE)
    fun provideRoutineFirestoreCollection(firestore: FirebaseFirestore): CollectionReference = firestore.collection(Routine.REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(Exercise.REFERENCE)
    fun provideExerciseFirestoreCollection(firestore: FirebaseFirestore): CollectionReference = firestore.collection(Exercise.REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(GenericData.CATEGORY_REFERENCE)
    fun provideCategoryFirestoreCollection(firestore: FirebaseFirestore): CollectionReference = firestore.collection(GenericData.CATEGORY_REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(GenericData.EQUIPMENT_REFERENCE)
    fun provideEquipmentFirestoreCollection(firestore: FirebaseFirestore): CollectionReference = firestore.collection(GenericData.EQUIPMENT_REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(GenericData.MUSCLE_REFERENCE)
    fun provideMuscleFirestoreCollection(firestore: FirebaseFirestore): CollectionReference = firestore.collection(GenericData.MUSCLE_REFERENCE)

}