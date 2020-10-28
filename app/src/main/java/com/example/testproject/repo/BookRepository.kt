package com.example.testproject.repo

import com.example.testproject.model.Book

interface BookRepository {

    suspend fun getBooks() : List<Book>

}