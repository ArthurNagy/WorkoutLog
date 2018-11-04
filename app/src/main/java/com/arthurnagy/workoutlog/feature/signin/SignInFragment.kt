package com.arthurnagy.workoutlog.feature.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.arthurnagy.workoutlog.SignInBinding
import com.arthurnagy.workoutlog.feature.WorkoutLogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : WorkoutLogFragment() {

    private lateinit var binding: SignInBinding
    private val viewModel by viewModel<SignInViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SignInBinding.inflate(inflater)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun provideToolbar(): Toolbar {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance() = SignInFragment()
    }

}
