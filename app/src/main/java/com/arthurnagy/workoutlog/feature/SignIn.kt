package com.arthurnagy.workoutlog.feature

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.feature.shared.showSnackbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse

interface SignIn {

    val signInHost: Fragment

    fun onSignedIn()

    fun signIn() {
        signInHost.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                .build(),
            RC_SIGN_IN
        )
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                onSignedIn()
            } else {
                when {
                    response == null -> signInHost.view?.showSnackbar(R.string.sign_in_cancelled)
                    response.error?.errorCode == ErrorCodes.NO_NETWORK -> signInHost.view?.showSnackbar(R.string.no_internet_connection)
                    else -> signInHost.view?.showSnackbar(R.string.unknown_error)
                }
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }

}