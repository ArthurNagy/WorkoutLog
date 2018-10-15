package com.arthurnagy.workoutlog.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.arthurnagy.workoutlog.UserProfileBinding
import com.arthurnagy.workoutlog.feature.WorkoutLogFragment

class UserProfileFragment : WorkoutLogFragment() {

    private lateinit var binding: UserProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = UserProfileBinding.inflate(inflater)
        NavigationUI.setupWithNavController(binding.appbar.toolbar, findNavController())
        return binding.root
    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar

}