package com.example.testproject.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testproject.model.Book

class FakeBookRepository : BookRepository {

    private val books = mutableListOf<Book>()

    private val booksData = MutableLiveData<List<Book>>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun getBooks(): LiveData<List<Book>> {
        return booksData
    }

    override suspend fun updateList() {
        if(shouldReturnNetworkError){
            throw Exception()
        } else{
            updateLiveData()
        }
    }

    override suspend fun updateBook(book: Book) {
        var bookIndex: Int = -1
        books.forEachIndexed { index, item ->
            if (item.id == book.id) {
                bookIndex = index
                return@forEachIndexed
            }
        }
        books[bookIndex] = book
        updateLiveData()
    }

    override suspend fun clearData() {
        books.clear()
        updateLiveData()
    }

    private fun updateLiveData() {
        booksData.postValue(books)
    }
}