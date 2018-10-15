package com.arthurnagy.workoutlog.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arthurnagy.workoutlog.HomeBinding
import com.arthurnagy.workoutlog.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeBinding = DataBindingUtil.setContentView<HomeBinding>(this, R.layout.home_activity)

//        val navController = Navigation.findNavController(this, R.id.nav_host)
//
//        setSupportActionBar(homeBinding.toolbar)
//        NavigationUI.setupWithNavController(homeBinding.toolbar, navController)
    }
}
