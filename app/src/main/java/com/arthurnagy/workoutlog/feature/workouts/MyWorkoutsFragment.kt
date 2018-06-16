package com.arthurnagy.workoutlog.feature.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arthurnagy.workoutlog.MyWorkoutsBinding

class MyWorkoutsFragment : Fragment() {

    private lateinit var binding: MyWorkoutsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return MyWorkoutsBinding.inflate(inflater, container, false).also { binding = it }.root
    }

}