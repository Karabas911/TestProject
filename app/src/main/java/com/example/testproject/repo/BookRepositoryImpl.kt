package com.example.testproject.repo

import android.util.Log
import com.example.testproject.database.BookDao
import com.example.testproject.model.Book
import com.example.testproject.model.BookRemote
import com.example.testproject.network.ApiBookService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepositoryImpl(
    private val apiBookService: ApiBookService,
    private val bookDao: BookDao
) : BookRepository {

    override fun getBooks() = bookDao.getBooks()

    override suspend fun updateList() {
        val response = apiBookService.searchBook("Martin", "ebook", "UA", 50)
        if (response.isSuccessful) {
            val bookList = wrapBooks(response.body()?.booksRemote)
            bookDao.updateBooksList(bookList)
        } else {
            throw Exception()
        }
    }

    override suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    override suspend fun clearData() {
        bookDao.deleteAll()
    }

    private suspend fun wrapBooks(booksRemote: List<BookRemote>?): List<Book> {
        return withContext(Dispatchers.Default) {
            val booksList = arrayListOf<Book>()
            val localBooks = bookDao.getBooksList()
            booksRemote?.forEach { remote ->
                val book = Book(
                    remote.trackId,
                    remote.trackName,
                    remote.artworkUrl100,
                    remote.artistName,
                    remote.description ?: "",
                    remote.price
                )
                localBooks?.forEach { local ->
                    if (book.id == local.id) {
                        book.favorite = local.favorite
                    }
                }
                booksList.add(book)
            }
            Log.d("BookRepo", booksList.toString())
            booksList
        }
    }

}