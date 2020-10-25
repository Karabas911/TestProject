package com.example.testproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testproject.BaseFragment
import com.example.testproject.Resource
import com.example.testproject.databinding.FragmentSignUpBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private var binding: FragmentSignUpBinding? = null

    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        this.binding = binding
        binding.login.setOnClickListener { backToLogin() }
        binding.register.setOnClickListener { onRegisterClicked() }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getSighUpEvent().observe(viewLifecycleOwner, { onSighUpEventChanged(it) })
    }

    private fun onSighUpEventChanged(resource: Resource<Int>) {
        when (resource.status) {
            Status.SUCCESS -> {

            }
            Status.ERROR -> {
                hideProgress()
                handleError(resource.errorReason, resource.msgRes)
            }
            Status.LOADING -> showProgress()
        }
    }

    private fun handleError(errorReason: Int, msgRes: Int) {
        val errorMessage = getString(msgRes)
        when (errorReason) {
            SignUpViewModel.ERROR_REASON_EMAIL -> binding?.let { it.email.error = errorMessage }

            SignUpViewModel.ERROR_REASON_PHONE -> binding?.let { it.phone.error = errorMessage }

            SignUpViewModel.ERROR_REASON_PASSWORD ->
                binding?.let { it.password.error = errorMessage }

            SignUpViewModel.ERROR_REASON_CONFIRM_PASSWORD ->
                binding?.let { it.confirmPassword.error = errorMessage }
        }
    }

    private fun onRegisterClicked() {
        binding?.let {
            val email = it.email.text.toString()
            val phone = it.phone.text.toString()
            val password = it.password.text.toString()
            val confirmPassword = it.confirmPassword.text.toString()
            viewModel.registerNewUser(email, phone, password, confirmPassword)
        }
    }

    private fun backToLogin() {
        findNavController().popBackStack()
    }
}