package com.arthurnagy.workoutlog.feature.today

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.TodayBinding
import com.arthurnagy.workoutlog.feature.SignIn
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogFragment
import com.arthurnagy.workoutlog.feature.shared.binding
import com.arthurnagy.workoutlog.feature.shared.consumeOptionsItemSelected
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodayFragment : WorkoutLogFragment<TodayBinding, TodayViewModel>(), SignIn {

    override val binding: TodayBinding by binding(R.layout.today_fragment)
    override val viewModel: TodayViewModel by viewModel()

    override fun onCreateView() {
        setHasOptionsMenu(true)

        binding.appbar.collapsingToolbar.title = getString(R.string.title_today)

        binding.workoutButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_todayFragment_to_workoutFragment))

        viewModel.event.observe(viewLifecycleOwner, Observer { event ->
            event.consume()?.let {
                when (it) {
                    is TodayViewModel.TodayView.Profile -> findNavController().navigate(R.id.action_todayFragment_to_userProfileFragment)
                    is TodayViewModel.TodayView.SignIn -> signIn()
                }
            }
        })
    }

    override val signInHost: Fragment get() = this

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.today, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.user_profile -> consumeOptionsItemSelected { viewModel.userProfileSelected() }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSignedIn() = viewModel.userSignedIn()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super<WorkoutLogFragment>.onActivityResult(requestCode, resultCode, data)
        super<SignIn>.onActivityResult(requestCode, resultCode, data)
    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar

}