package com.arthurnagy.workoutlog.feature.account

import androidx.lifecycle.ViewModel
import com.arthurnagy.workoutlog.core.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class AccountMenuModule {

    @ContributesAndroidInjector
    abstract fun bindAccountMenuFragment(): AccountMenuFragment

    @Binds
    @IntoMap
    @ViewModelKey(AccountMenuViewModel::class)
    abstract fun bindAccountMenuViewModel(accountMenuViewModel: AccountMenuViewModel): ViewModel

}