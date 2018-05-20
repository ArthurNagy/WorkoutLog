package com.arthurnagy.workoutlog.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arthurnagy.workoutlog.MainBinding
import com.arthurnagy.workoutlog.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<MainBinding>(this, R.layout.activity_home)?.also {
            setSupportActionBar(it.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)

        }
    }

}
