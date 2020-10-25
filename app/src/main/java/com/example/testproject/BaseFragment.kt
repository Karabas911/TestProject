package com.example.testproject

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testproject.ui.ProgressDialog

open class BaseFragment : Fragment() {

    private var progress: ProgressDialog? = null

    fun showProgress() {
        hideProgress()
        progress = ProgressDialog.newInstance()
        progress?.show(childFragmentManager, ProgressDialog.TAG)
    }

    fun hideProgress() {
        if (progress != null) {
            progress?.dismiss()
            progress = null
        }
    }

    fun showMessage(msgRes: Int) {
        Toast.makeText(requireContext(), msgRes, Toast.LENGTH_LONG).show()
    }
}