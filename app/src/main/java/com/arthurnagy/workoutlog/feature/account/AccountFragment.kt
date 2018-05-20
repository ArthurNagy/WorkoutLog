package com.arthurnagy.workoutlog.feature.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.workoutlog.AccountBinding
import com.arthurnagy.workoutlog.feature.shared.RoundedBottomSheetDialogFragment

class AccountFragment : RoundedBottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return AccountBinding.inflate(inflater, container, false).root
    }

}