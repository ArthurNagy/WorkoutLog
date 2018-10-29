package com.arthurnagy.workoutlog.feature.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.arthurnagy.workoutlog.CreateWorkoutBinding
import com.arthurnagy.workoutlog.feature.WorkoutLogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateWorkoutFragment : WorkoutLogFragment() {

    private val viewModel: CreateWorkoutViewModel by viewModel()
    private lateinit var binding: CreateWorkoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CreateWorkoutBinding.inflate(inflater)
        NavigationUI.setupWithNavController(binding.appbar.toolbar, findNavController())
        binding.viewModel = viewModel

        return binding.root
    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar
}