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
import com.example.testproject.prefs.PreferenceWrapper
import com.example.testproject.repo.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository,
    private val prefs: PreferenceWrapper
) : ViewModel() {

    companion object {
        const val TAG = "SignUpViewModel"
        private const val MIN_PASSWORD_LENGTH = 4
        const val ERROR_REASON_EMAIL = 1
        const val ERROR_REASON_PHONE = 2
        const val ERROR_REASON_PASSWORD = 3
        const val ERROR_REASON_CONFIRM_PASSWORD = 4
        const val ERROR_REASON_DATABASE = 5
    }

    private val sighUpEvent = SingleLiveEvent<Resource<Int>>()

    fun getSighUpEvent(): LiveData<Resource<Int>> = sighUpEvent

    fun registerNewUser(email: String, phone: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (emailIsValid(email)
                && phoneIsValid(phone)
                && passwordIsValid(password, confirmPassword)
            ) {
                sighUpEvent.value = Resource.loading()
                delay(3000)
                val userId = prefs.getNextUserId()
                Log.d(TAG, "Verified: userId = $userId")
                val user = User(userId, email, phone, password)
                val savedUserRows = repository.saveUserData(user)
                if (savedUserRows > 0){
                    sighUpEvent.value = Resource.success()
                    prefs.setLoginUserId(userId)
                }else{
                    sighUpEvent.value = Resource.error(ERROR_REASON_DATABASE, R.string.error_save)
                }
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
                sighUpEvent.value =
                    Resource.error(ERROR_REASON_EMAIL, R.string.error_registered_email)
                false
            }
            else -> true
        }
    }


    private fun phoneIsValid(phone: String): Boolean {
        return when {
            TextUtils.isEmpty(phone) -> {
                sighUpEvent.value = Resource.error(ERROR_REASON_PHONE, R.string.error_provide_phone)
                false
            }
            !PhoneNumberUtils.isGlobalPhoneNumber(phone) -> {
                sighUpEvent.value = Resource.error(ERROR_REASON_PHONE, R.string.error_valid_phone)
                false
            }
            else -> true
        }
    }

    private fun passwordIsValid(password: String, confirmPassword: String): Boolean {
        return when {
            TextUtils.isEmpty(password) -> {
                sighUpEvent.value =
                    Resource.error(ERROR_REASON_PASSWORD, R.string.error_provide_password)
                false
            }
            TextUtils.isEmpty(confirmPassword) -> {
                sighUpEvent.value = Resource.error(
                    ERROR_REASON_CONFIRM_PASSWORD,
                    R.string.error_provide_confirm_password
                )
                false
            }
            password.length < MIN_PASSWORD_LENGTH -> {
                sighUpEvent.value =
                    Resource.error(ERROR_REASON_PASSWORD, R.string.error_valid_password)
                false
            }
            password != confirmPassword -> {
                sighUpEvent.value = Resource.error(
                    ERROR_REASON_CONFIRM_PASSWORD,
                    R.string.error_valid_confirm_password
                )
                false
            }
            else -> true
        }
    }
}