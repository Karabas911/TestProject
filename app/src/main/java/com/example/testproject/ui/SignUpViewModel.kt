package com.example.testproject.ui

import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.util.Log
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.R
import com.example.testproject.Resource
import com.example.testproject.SingleLiveEvent
import com.example.testproject.model.User
import com.example.testproject.repo.AuthRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: AuthRepository) : ViewModel() {

    companion object {
        const val TAG = "SignUpViewModel"

        private const val MIN_PASSWORD_LENGTH = 4

        const val ERROR_REASON_EMAIL = 1
        const val ERROR_REASON_PHONE = 2
        const val ERROR_REASON_PASSWORD = 3
        const val ERROR_REASON_CONFIRM_PASSWORD = 4
    }

    private val sighUpEvent = SingleLiveEvent<Resource<Int>>()

    fun getSighUpEvent(): LiveData<Resource<Int>> = sighUpEvent

    fun registerNewUser(email: String, phone: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (emailIsValid(email)
                && phoneIsValid(phone)
                && passwordIsValid(password, confirmPassword)
            ) {
                Log.d(TAG, "Verified")
                val user = User(1, email, phone, password)
            }
        }
    }

    private suspend fun emailIsValid(email: String): Boolean {
        val user = repository.findByEmail(email)
        return when {
            TextUtils.isEmpty(email) -> {
                sighUpEvent.value = Resource.error(ERROR_REASON_EMAIL, R.string.error_provide_email)
                false
            }
            !PatternsCompat.EMAIL_ADDRESS.matcher(email).matches() -> {
                sighUpEvent.value = Resource.error(ERROR_REASON_EMAIL, R.string.error_valid_email)
                false
            }
            user != null -> {
                sighUpEvent.value = Resource.error(ERROR_REASON_EMAIL, R.string.error_registered_email)
                false
            }
            else -> true
        }
    }


    private fun phoneIsValid(phone: String): Boolean {
        return when {
            TextUtils.isEmpty(phone) -> {
                false
            }
            !PhoneNumberUtils.isGlobalPhoneNumber(phone) -> {
                false
            }
            else -> true
        }
    }

    private fun passwordIsValid(password: String, confirmPassword: String): Boolean {
        return when {

            TextUtils.isEmpty(password) -> {

                false
            }

            TextUtils.isEmpty(confirmPassword) -> {

                false
            }

            password.length < MIN_PASSWORD_LENGTH -> {

                false
            }

            !password.equals(confirmPassword) -> {

                false
            }

            else -> true

        }
    }
}