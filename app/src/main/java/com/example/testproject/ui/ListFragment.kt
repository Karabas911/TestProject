package com.example.testproject.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.databinding.FragmentListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    companion object {

    }

    private val viewModel: ListViewModel by viewModel()

    private var binding: FragmentListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        initUI()
        return binding!!.root
    }

    private fun initUI() {
        binding?.run {
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_item_logout -> {
                        showLogOutDialog()
                        true
                    }
                    else -> false
                }
            }
            toolbar.setTitleTextColor(Color.WHITE)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getUserData().observe(viewLifecycleOwner, Observer { user ->
            binding?.toolbar?.title = user.email
        })
        viewModel.getListEvent().observe(viewLifecycleOwner, {onListEvent(it)})
    }

    private fun onListEvent(eventKey: Int) {
        when(eventKey){
            ListViewModel.EVENT_ON_USER_LOGGED_OUT -> navigateToAuth()
        }
    }

    private fun showLogOutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.log_out_title))
            .setMessage(R.string.logout_message)
            .setPositiveButton(R.string.logout) { _, _ -> onLogoutClicked() }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun navigateToAuth() {
        findNavController().navigate(R.id.action_listFragment_to_authFragment)
    }

    private fun onLogoutClicked() {
        viewModel.logOut()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}