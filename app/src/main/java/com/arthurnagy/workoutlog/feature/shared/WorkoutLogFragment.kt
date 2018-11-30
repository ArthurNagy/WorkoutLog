package com.arthurnagy.workoutlog.feature.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.arthurnagy.workoutlog.BR

abstract class WorkoutLogFragment<VB : ViewDataBinding, VM : WorkoutLogViewModel> : Fragment() {

    abstract val binding: VB
    abstract val viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.setVariable(BR.viewModel, viewModel)
        NavigationUI.setupWithNavController(provideToolbar(), findNavController())
        onCreateView()
        return binding.root
    }

    abstract fun onCreateView()

    abstract fun provideToolbar(): Toolbar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireAppCompatActivity().setSupportActionBar(provideToolbar())
    }

}