package com.arthurnagy.workoutlog.feature.shared

import android.app.Dialog
import android.os.Bundle
import com.arthurnagy.workoutlog.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Uses a custom BottomSheetDialog theme which sets a rounded background to the dialog and doesn't dim the navigation bar
 */
open class RoundedBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

}