package com.arthurnagy.workoutlog.feature.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.TodayBinding
import com.arthurnagy.workoutlog.core.consumeOptionsItemSelected
import com.arthurnagy.workoutlog.feature.WorkoutLogFragment
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

        return todayBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.today, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.user_profile -> consumeOptionsItemSelected {
            findNavController().navigate(R.id.action_todayFragment_to_userProfileFragment)
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun provideToolbar(): Toolbar = todayBinding.appbar.toolbar

}