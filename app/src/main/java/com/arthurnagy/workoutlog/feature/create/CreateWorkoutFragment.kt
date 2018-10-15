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

class CreateWorkoutFragment : WorkoutLogFragment() {

    private lateinit var binding: CreateWorkoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CreateWorkoutBinding.inflate(inflater)
        NavigationUI.setupWithNavController(binding.appbar.toolbar, findNavController())

        return binding.root
    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar
}