package com.arthurnagy.workoutlog.feature.profile

import androidx.appcompat.widget.Toolbar
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.UserProfileBinding
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogFragment
import com.arthurnagy.workoutlog.feature.shared.binding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileFragment : WorkoutLogFragment<UserProfileBinding, UserProfileViewModel>() {

    override val binding: UserProfileBinding by binding(R.layout.profile_fragment)
    override val viewModel: UserProfileViewModel by viewModel()

    override fun onCreateView() {
        binding.appbar.collapsingToolbar.title = getString(R.string.profile)
    }

    override fun provideToolbar(): Toolbar = binding.appbar.toolbar

}