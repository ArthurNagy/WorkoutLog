package com.arthurnagy.workoutlog.feature.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.WorkoutsBinding
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogFragment
import com.arthurnagy.workoutlog.feature.shared.provideViewModel
import com.google.android.material.appbar.AppBarLayout
import javax.inject.Inject

class WorkoutsFragment : WorkoutLogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: WorkoutsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return WorkoutsBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = provideViewModel<WorkoutsViewModel>(viewModelFactory)
        val appBarElevation = resources.getDimensionPixelSize(R.dimen.app_bar_elevation)
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
            val currentElevation: Float = if (offset == 0) 0f else appBarElevation.toFloat()
            if (ViewCompat.getElevation(appBarLayout) != currentElevation) ViewCompat.setElevation(
                appBarLayout,
                currentElevation
            )
        })
    }

}