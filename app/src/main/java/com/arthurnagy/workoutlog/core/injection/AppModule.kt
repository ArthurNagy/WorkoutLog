package com.arthurnagy.workoutlog.core.injection

import android.content.Context
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.WorkoutLogApp
import com.arthurnagy.workoutlog.core.model.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Named

@Module
object AppModule {

    @JvmStatic
    @Provides
    @AppContext
    fun provideAppContext(workoutLogApp: WorkoutLogApp): Context = workoutLogApp

    @JvmStatic
    @Provides
    fun provideGoogleSignInClient(@AppContext context: Context): GoogleSignInClient =
        GoogleSignIn.getClient(
            context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .build()
        )

    @JvmStatic
    @Provides
    @Reusable
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

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