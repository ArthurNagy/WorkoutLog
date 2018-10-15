package com.arthurnagy.workoutlog.feature.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.arthurnagy.workoutlog.WorkoutBinding
import com.arthurnagy.workoutlog.feature.WorkoutLogFragment

class WorkoutFragment : WorkoutLogFragment() {

    private lateinit var binding: WorkoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = WorkoutBinding.inflate(inflater)
        NavigationUI.setupWithNavController(binding.appbar.toolbar, findNavController())
        return binding.root
    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar
}