package com.example.testproject.ui

import android.text.TextUtils
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.R
import com.example.testproject.Resource
import com.example.testproject.SingleLiveEvent
import com.example.testproject.prefs.PreferenceWrapper
import com.example.testproject.repo.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repository: AuthRepository,
    private val prefs: PreferenceWrapper
) : ViewModel() {

    companion object {
        const val TAG = "SignInViewModel"
        const val ERROR_REASON_EMAIL = 1
        const val ERROR_EMPTY_FIELD = 2
        const val ERROR_NOT_REGISTERED = 3
        const val ERROR_PASSWORD = 4
    }

    private val sighInEvent = SingleLiveEvent<Resource<Int>>()

    fun getSighInEvent(): LiveData<Resource<Int>> = sighInEvent

    fun onLoginClicked(email: String, password: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            sighInEvent.value = Resource.error(ERROR_EMPTY_FIELD, R.string.error_empty_field)
            return
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            sighInEvent.value = Resource.error(ERROR_REASON_EMAIL, R.string.error_valid_email)
            return
        }
        proceedLogin(email, password)
    }

    private fun proceedLogin(email: String, password: String) {
        viewModelScope.launch {
            sighInEvent.value = Resource.loading()
            val user = repository.findByEmail(email)
            if (user == null) {
                sighInEvent.value =
                    Resource.error(ERROR_NOT_REGISTERED, R.string.error_not_registered)
                return@launch
            }
            if (user.password != password) {
                sighInEvent.value = Resource.error(ERROR_PASSWORD, R.string.error_wrong_password)
            } else {
                val userId = user.id
                prefs.setLoginUserId(userId)
                delay(3000)
                sighInEvent.value = Resource.success()
            }
        }
    }
}