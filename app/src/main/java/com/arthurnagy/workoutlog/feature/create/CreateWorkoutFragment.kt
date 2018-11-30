package com.arthurnagy.workoutlog.feature.create

import androidx.appcompat.widget.Toolbar
import com.arthurnagy.workoutlog.CreateWorkoutBinding
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogFragment
import com.arthurnagy.workoutlog.feature.shared.binding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateWorkoutFragment : WorkoutLogFragment<CreateWorkoutBinding, CreateWorkoutViewModel>() {

    override val binding: CreateWorkoutBinding by binding(R.layout.create_workout_fragment)
    override val viewModel: CreateWorkoutViewModel by viewModel()

    override fun onCreateView() {
    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar
}