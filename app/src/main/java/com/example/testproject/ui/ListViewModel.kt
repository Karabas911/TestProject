package com.example.testproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.Resource
import com.example.testproject.SingleLiveEvent
import com.example.testproject.model.ListFragmentModel
import com.example.testproject.model.User
import com.example.testproject.prefs.PreferenceWrapper
import com.example.testproject.repo.AuthRepository
import com.example.testproject.repo.BookRepository
import kotlinx.coroutines.launch

class ListViewModel(
    private val prefs: PreferenceWrapper,
    private val authRepository: AuthRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

    companion object{
        const val EVENT_ON_USER_LOGGED_OUT = 1
    }

    private val listFragmentModel = MutableLiveData<Resource<ListFragmentModel>>()

    private val userData = MutableLiveData<User>()

    private lateinit var user: User

    private val listEvent = SingleLiveEvent<Int>()

    fun getUserData(): LiveData<User> = userData

    fun getListEvent(): LiveData<Int> = listEvent

    init {
        viewModelScope.launch {
            val userId = prefs.getLoginUserId()
            user = authRepository.findById(userId)
            userData.value = user
        }
    }

    fun logOut() {
        prefs.removeLoggedInId()
        listEvent.value = EVENT_ON_USER_LOGGED_OUT
    }
}