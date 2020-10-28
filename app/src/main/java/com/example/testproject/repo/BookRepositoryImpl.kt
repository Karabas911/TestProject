package com.example.testproject.repo

import android.util.Log
import com.example.testproject.model.Book
import com.example.testproject.model.BookRemote
import com.example.testproject.network.ApiBookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepositoryImpl(private val apiBookService: ApiBookService) : BookRepository {

    override suspend fun getBooks(): List<Book> {
        val response = apiBookService.searchBook("Martin", "ebook", "UA", 50)
        if (response.isSuccessful) {
            val bookList = wrapBooks(response.body()?.booksRemote)
            return bookList
        } else {
            throw Exception()
        }
    }

    private suspend fun wrapBooks(booksRemote: List<BookRemote>?): List<Book> {
        return withContext(Dispatchers.Default) {
            val booksList = arrayListOf<Book>()
            booksRemote?.forEach { remote ->
                booksList.add(
                    Book(
                        0,
                        remote.trackName,
                        remote.artistName,
                        remote.description,
                        remote.price
                    )
                )
                Log.d("BookRepo", booksList.toString())
            }
            booksList
        }
    }

}