package com.example.testproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testproject.BaseFragment
import com.example.testproject.R
import com.example.testproject.Resource
import com.example.testproject.databinding.FragmentSignInBinding
import com.example.testproject.ui.SignInViewModel.Companion.ERROR_EMPTY_FIELD
import com.example.testproject.ui.SignInViewModel.Companion.ERROR_NOT_REGISTERED
import com.example.testproject.ui.SignInViewModel.Companion.ERROR_PASSWORD
import com.example.testproject.ui.SignInViewModel.Companion.ERROR_REASON_EMAIL
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment() {

    private var binding: FragmentSignInBinding? = null

    private val viewModel: SignInViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        this.binding = binding
        binding.register.setOnClickListener { onRegisterClick() }
        binding.login.setOnClickListener { onLoginClicked() }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getSighInEvent().observe(viewLifecycleOwner, { onSighInEventChanged(it) })
    }

    private fun onLoginClicked() {
        binding?.let {
            viewModel.onLoginClicked(it.email.text.toString(), it.password.text.toString())
        }
    }

    private fun onRegisterClick() {
        findNavController().navigate(R.id.action_authFragment_to_signUpFragment)
    }

    private fun onSighInEventChanged(resource: Resource<Int>) {
        when (resource.status) {
            Status.SUCCESS -> {
                hideProgress()
                navigateToMain()
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
            ERROR_EMPTY_FIELD, ERROR_NOT_REGISTERED -> showMessage(msgRes)

            ERROR_REASON_EMAIL -> binding?.let { it.email.error = errorMessage }

            ERROR_PASSWORD -> binding?.let { it.password.error = errorMessage }
        }
    }

    private fun navigateToMain() {
        findNavController().navigate(R.id.action_authFragment_to_listFragment)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}