package com.example.recipeapp.bases

import android.util.Log
import androidx.fragment.app.Fragment
import com.example.recipeapp.dialogs.ProgressBarDialog


abstract class BaseFragment : Fragment() {

    private val spinnerDialog by lazy { ProgressBarDialog(requireActivity()) }


    protected fun showSpinner() {
        if (!spinnerDialog.isShowing) {
            spinnerDialog.show()
        }
    }

    protected fun dismissSpinner() {
        if (spinnerDialog.isShowing) {
            spinnerDialog.dismiss()
        }
    }

}