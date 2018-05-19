package com.arthurnagy.workoutlog.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arthurnagy.workoutlog.MainBinding
import com.arthurnagy.workoutlog.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<MainBinding>(this, R.layout.activity_main)?.also {
            setSupportActionBar(it.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

}
