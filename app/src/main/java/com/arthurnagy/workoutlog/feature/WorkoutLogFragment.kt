package com.arthurnagy.workoutlog.feature

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.arthurnagy.workoutlog.feature.shared.requireAppCompatActivity

abstract class WorkoutLogFragment : Fragment() {

    abstract fun provideToolbar(): Toolbar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireAppCompatActivity().setSupportActionBar(provideToolbar())
    }

}