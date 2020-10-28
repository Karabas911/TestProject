package com.example.testproject.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testproject.model.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun getBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books")
    fun getBooksList(): List<Book>?

    @Insert
    suspend fun insert(books: List<Book>)

    @Query("DELETE FROM books")
    suspend fun deleteAll()

    @Transaction
    suspend fun updateBooksList(books: List<Book>) {
        deleteAll()
        insert(books)
    }

    @Update
    suspend fun updateBook(vararg book: Book)
}