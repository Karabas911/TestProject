package com.example.testproject.repo

import androidx.lifecycle.LiveData
import com.example.testproject.model.Book

interface BookRepository {

    fun getBooks(): LiveData<List<Book>>

    suspend fun updateList()

    suspend fun updateBook(book:Book)

    suspend fun clearData()

}