package com.arthurnagy.workoutlog.core.injection

import android.content.Context
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.WorkoutLogApp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.Reusable

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

}