package com.arthurnagy.workoutlog.feature.shared

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

abstract class WorkoutLogFragment : Fragment() {

    abstract fun provideToolbar(): Toolbar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireAppCompatActivity().setSupportActionBar(provideToolbar())
    }

}