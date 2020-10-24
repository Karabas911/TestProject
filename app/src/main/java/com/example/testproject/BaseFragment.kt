package com.example.testproject

import androidx.fragment.app.Fragment
import com.example.testproject.ui.ProgressDialog

open class BaseFragment : Fragment() {

    private var progress: ProgressDialog? = null

    fun showProgress() {
        cancelProgress()
        progress = ProgressDialog.newInstance()
        progress?.show(childFragmentManager, ProgressDialog.TAG)
    }

    private fun cancelProgress() {
        if (progress != null) {
            progress?.dismiss()
            progress = null
        }
    }
}