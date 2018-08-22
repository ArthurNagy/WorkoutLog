package com.arthurnagy.workoutlog.feature.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.arthurnagy.workoutlog.HomeBinding
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.feature.account.AccountMenuFragment
import com.arthurnagy.workoutlog.feature.shared.WorkoutLogActivity

class HomeActivity : WorkoutLogActivity() {

    private val accountMenuFragment: AccountMenuFragment by lazy { AccountMenuFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<HomeBinding>(this, R.layout.activity_home)?.also { homeBinding ->
            setSupportActionBar(homeBinding.bottomAppBar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            homeBinding.bottomAppBar.setNavigationOnClickListener {
                accountMenuFragment.show(supportFragmentManager, accountMenuFragment.tag)
            }
        }
    }

}
