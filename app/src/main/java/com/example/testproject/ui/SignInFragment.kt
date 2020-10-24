package com.example.testproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testproject.BaseFragment
import com.example.testproject.R
import com.example.testproject.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private var binding: FragmentSignInBinding? = null

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        this.binding = binding
        binding.register.setOnClickListener { onRegisterClick() }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
    }

    private fun onRegisterClick() {
        findNavController().navigate(R.id.action_authFragment_to_signUpFragment)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}