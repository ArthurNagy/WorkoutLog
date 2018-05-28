package com.arthurnagy.workoutlog.feature.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.arthurnagy.workoutlog.AccountMenuBinding
import com.arthurnagy.workoutlog.feature.shared.RoundedBottomSheetDialogFragment
import com.arthurnagy.workoutlog.feature.shared.provideViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class AccountMenuFragment : RoundedBottomSheetDialogFragment() {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: AccountMenuBinding
    private lateinit var viewModel: AccountMenuViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AccountMenuBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = provideViewModel(viewModelFactory)
        binding.viewModel = viewModel
        binding.signInButton.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                viewModel.signIn(GoogleAuthProvider.getCredential(account.idToken, null))
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately

            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 42
    }

}