package com.arthurnagy.workoutlog.feature.workout

import androidx.appcompat.widget.Toolbar
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.WorkoutBinding
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogFragment
import com.arthurnagy.workoutlog.feature.shared.binding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WorkoutFragment : WorkoutLogFragment<WorkoutBinding, WorkoutViewModel>() {

    override val binding: WorkoutBinding by binding(R.layout.workout_fragment)
    override val viewModel: WorkoutViewModel by viewModel()

    override fun onCreateView() {

    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar
}