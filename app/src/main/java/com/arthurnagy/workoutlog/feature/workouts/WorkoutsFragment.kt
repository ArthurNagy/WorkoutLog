package com.arthurnagy.workoutlog.feature.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.WorkoutsBinding
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.absoluteValue

class WorkoutsFragment : Fragment() {

    private val viewModel: WorkoutsViewModel by viewModel()
    private lateinit var binding: WorkoutsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return WorkoutsBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appBarElevation = resources.getDimensionPixelSize(R.dimen.app_bar_elevation).toFloat()
        val toolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height)
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
            val appBarOffset = offset.absoluteValue
            val currentElevation = if (appBarOffset >= toolbarHeight) appBarElevation else 0f
            if (ViewCompat.getElevation(appBarLayout) != currentElevation) ViewCompat.setElevation(appBarLayout, currentElevation)
        })
    }

}