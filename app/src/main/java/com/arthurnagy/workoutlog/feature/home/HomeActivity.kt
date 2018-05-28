package com.arthurnagy.workoutlog.feature.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.arthurnagy.workoutlog.MainBinding
import com.arthurnagy.workoutlog.R
import com.arthurnagy.workoutlog.feature.account.AccountMenuFragment
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    private val accountMenuFragment: AccountMenuFragment by lazy { AccountMenuFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<MainBinding>(this, R.layout.activity_home)?.also {
            setSupportActionBar(it.bottomAppBar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            it.bottomAppBar.setNavigationOnClickListener {
                accountMenuFragment.show(supportFragmentManager, accountMenuFragment.tag)
            }
        }
    }

}
