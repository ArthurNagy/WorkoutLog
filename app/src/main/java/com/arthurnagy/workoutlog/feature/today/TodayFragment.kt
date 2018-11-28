package com.arthurnagy.workoutlog.feature.today

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.TodayBinding
import com.arthurnagy.workoutlog.core.consumeOptionsItemSelected
import com.arthurnagy.workoutlog.feature.WorkoutLogFragment
import com.firebase.ui.auth.AuthUI
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodayFragment : WorkoutLogFragment() {

    private lateinit var todayBinding: TodayBinding
    private val viewModel by viewModel<TodayViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        todayBinding = TodayBinding.inflate(inflater)

        todayBinding.setLifecycleOwner(viewLifecycleOwner)
        todayBinding.viewModel = viewModel

        todayBinding.appbar.collapsingToolbar.title = getString(R.string.title_today)

        todayBinding.workoutButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_todayFragment_to_workoutFragment))

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                is TodayViewModel.Event.ProfileEvent -> findNavController().navigate(R.id.action_todayFragment_to_userProfileFragment)
                is TodayViewModel.Event.SignInEvent -> startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                        .build(),
                    RC_SIGN_IN
                )
            }
        })

        return todayBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.today, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.user_profile -> consumeOptionsItemSelected { viewModel.userProfileSelected() }
        else -> super.onOptionsItemSelected(item)
    }

    override fun provideToolbar(): Toolbar = todayBinding.appbar.toolbar

    companion object {
        private const val RC_SIGN_IN = 123
    }

}