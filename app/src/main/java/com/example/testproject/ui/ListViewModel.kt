package com.example.testproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testproject.R
import com.example.testproject.Resource
import com.example.testproject.SingleLiveEvent
import com.example.testproject.model.Book
import com.example.testproject.model.User
import com.example.testproject.prefs.PreferenceWrapper
import com.example.testproject.repo.AuthRepository
import com.example.testproject.repo.BookRepository
import com.example.testproject.utils.isNetworkConnected
import kotlinx.coroutines.launch

class ListViewModel(
    private val prefs: PreferenceWrapper,
    private val authRepository: AuthRepository,
    private val bookRepository: BookRepository,
    app: Application
) : AndroidViewModel(app) {

    companion object {
        const val EVENT_ON_USER_LOGGED_OUT = 1
        const val EVENT_LIST_UPDATE_ERROR = 2
        const val EVENT_LOAD_SUCCESS = 3
    }

    private lateinit var user: User

    private val userData = MutableLiveData<User>()

    private val listEvent = SingleLiveEvent<Resource<Int>>()

    fun getUserData(): LiveData<User> = userData

    fun getListEvent(): LiveData<Resource<Int>> = listEvent

    fun getBookList(): LiveData<List<Book>> = bookRepository.getBooks()

    init {
        viewModelScope.launch {
            val userId = prefs.getLoginUserId()
            user = authRepository.findById(userId)
            userData.value = user
            updateBookList()
        }
    }

    private suspend fun updateBookList() {
        if (getApplication<Application>().isNetworkConnected()) {
            listEvent.value = Resource.loading()
            runCatching {
                bookRepository.updateList()
                listEvent.value = Resource.success(EVENT_LOAD_SUCCESS)
            }.onFailure { exc ->
                exc.printStackTrace()
                listEvent.value =
                    Resource.error(EVENT_LIST_UPDATE_ERROR, R.string.error_update_list)
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            prefs.removeLoggedInId()
            listEvent.value = Resource.success(EVENT_ON_USER_LOGGED_OUT)
            bookRepository.clearData()
        }
    }

    fun onLikeClicked(book: Book) {
        viewModelScope.launch {
            book.favorite = !book.favorite
            bookRepository.updateBook(book)
        }
    }
}