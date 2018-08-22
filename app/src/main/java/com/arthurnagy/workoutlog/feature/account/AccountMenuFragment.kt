package com.arthurnagy.workoutlog.feature.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.workoutlog.AccountMenuBinding
import com.arthurnagy.workoutlog.feature.shared.RoundedBottomSheetDialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountMenuFragment : RoundedBottomSheetDialogFragment() {

    private val googleSignInClient: GoogleSignInClient by inject()
    private val viewModel: AccountMenuViewModel by viewModel()
    private lateinit var binding: AccountMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AccountMenuBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setLifecycleOwner(viewLifecycleOwner)
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