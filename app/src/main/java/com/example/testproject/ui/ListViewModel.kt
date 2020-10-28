package com.example.testproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.Resource
import com.example.testproject.model.ListFragmentModel
import com.example.testproject.model.User
import com.example.testproject.prefs.PreferenceWrapper
import com.example.testproject.repo.AuthRepository
import kotlinx.coroutines.launch

class ListViewModel(
    private val prefs: PreferenceWrapper,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val listFragmentModel = MutableLiveData<Resource<ListFragmentModel>>()

    private val userData = MutableLiveData<User>()

    private lateinit var user: User

    fun getUserData(): LiveData<User> = userData

    init {
        viewModelScope.launch {
            val userId = prefs.getLoginUserId()
            user = authRepository.findById(userId)
            userData.value = user
        }
    }

    fun logOut() {

    }
}