package com.arthurnagy.workoutlog.feature.today

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.TodayBinding
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogFragment
import com.arthurnagy.workoutlog.feature.shared.consumeOptionsItemSelected
import com.arthurnagy.workoutlog.feature.shared.showSnackbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodayFragment : WorkoutLogFragment() {

    private lateinit var binding: TodayBinding
    private val viewModel by viewModel<TodayViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = TodayBinding.inflate(inflater)

        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewModel = viewModel

        binding.appbar.collapsingToolbar.title = getString(R.string.title_today)

        binding.workoutButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_todayFragment_to_workoutFragment))

        viewModel.event.observe(viewLifecycleOwner, Observer { event ->
            event.consume()?.let {
                when (it) {
                    is TodayViewModel.TodayView.Profile -> findNavController().navigate(R.id.action_todayFragment_to_userProfileFragment)
                    is TodayViewModel.TodayView.SignIn -> startActivityForResult(
                        AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                            .build(),
                        RC_SIGN_IN
                    )
                }
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.today, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.user_profile -> consumeOptionsItemSelected { viewModel.userProfileSelected() }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == RESULT_OK) {
                viewModel.userSignedIn()
            } else {
                when {
                    response == null -> binding.showSnackbar(R.string.sign_in_cancelled)
                    response.error?.errorCode == ErrorCodes.NO_NETWORK -> binding.showSnackbar(R.string.no_internet_connection)
                    else -> binding.showSnackbar(R.string.unknown_error)
                }
            }
        }
    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar

    companion object {
        private const val RC_SIGN_IN = 123
    }

}