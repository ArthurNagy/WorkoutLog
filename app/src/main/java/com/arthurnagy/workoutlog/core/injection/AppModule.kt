package com.arthurnagy.workoutlog.core.injection

import android.content.Context
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.WorkoutLogApp
import com.arthurnagy.workoutlog.core.model.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @JvmStatic
    @Provides
    @Reusable
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

    @JvmStatic
    @Provides
    @Reusable
    @Named(User.REFERENCE)
    fun provideUserDatabaseReference(firebaseDatabase: FirebaseDatabase) = firebaseDatabase.getReference(User.REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(Workout.REFERENCE)
    fun provideWorkoutDatabaseReference(firebaseDatabase: FirebaseDatabase) = firebaseDatabase.getReference(Workout.REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(Routine.REFERENCE)
    fun provideRoutineDatabaseReference(firebaseDatabase: FirebaseDatabase) = firebaseDatabase.getReference(Routine.REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(Exercise.REFERENCE)
    fun provideExerciseDatabaseReference(firebaseDatabase: FirebaseDatabase) = firebaseDatabase.getReference(Exercise.REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(GenericData.CATEGORY_REFERENCE)
    fun provideCategoryDatabaseReference(firebaseDatabase: FirebaseDatabase) = firebaseDatabase.getReference(GenericData.CATEGORY_REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(GenericData.EQUIPMENT_REFERENCE)
    fun provideEquipmentDatabaseReference(firebaseDatabase: FirebaseDatabase) = firebaseDatabase.getReference(GenericData.EQUIPMENT_REFERENCE)

    @JvmStatic
    @Provides
    @Reusable
    @Named(GenericData.MUSCLE_REFERENCE)
    fun provideMuscleDatabaseReference(firebaseDatabase: FirebaseDatabase) = firebaseDatabase.getReference(GenericData.MUSCLE_REFERENCE)

}