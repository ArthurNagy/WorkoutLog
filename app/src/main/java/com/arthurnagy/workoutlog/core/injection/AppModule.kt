package com.arthurnagy.workoutlog.core.injection

import android.content.Context
import com.arthurnagy.workoutlog.feature.shared.AppDispatchers
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {

    factory<GoogleSignInClient> {
        GoogleSignIn.getClient(
            get<Context>(), GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        )
    }

    factory<FirebaseAuth> { FirebaseAuth.getInstance() }

    factory {
        AppDispatchers(
            main = Dispatchers.Main,
            io = Dispatchers.IO,
            computation = Dispatchers.Default
        )
    }

}