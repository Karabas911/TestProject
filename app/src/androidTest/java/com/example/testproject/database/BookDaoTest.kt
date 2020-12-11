package com.example.testproject.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testproject.getOrAwaitValue
import com.example.testproject.model.Book
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class BookDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var bookDao: BookDao

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        bookDao = appDatabase.bookDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun insertItem() = runBlockingTest {
        val book = Book(
            1, "Hello Test", null, "Author", "Very big description",
            10.5, false
        )
        bookDao.insert(listOf(book))
        val resultList = bookDao.getBooks().getOrAwaitValue()
        assertThat(resultList).contains(book)
    }

    @Test
    fun deleteItem() = runBlockingTest {
        val book = Book(
            1, "Hello Test", null, "Author", "Very big description",
            10.5, false
        )
        bookDao.insert(listOf(book))
        bookDao.deleteAll()
        val resultList = bookDao.getBooks().getOrAwaitValue()
        assertThat(resultList).doesNotContain(book)
    }

    @Test
    fun updateAllList() = runBlockingTest {
        val firsList = arrayListOf<Book>()
        for (i in 1..10) {
            val book = Book(
                i, "Hello Test $i", null, "Author $i", "Very big description",
                10.5, false
            )
            firsList.add(book)
        }
        val secondList = arrayListOf<Book>()
        for (i in 15..30) {
            val book = Book(
                i, "Hello Test $i", null, "Author $i", "Very big description",
                10.5, false
            )
            secondList.add(book)
        }
        bookDao.insert(firsList)
        bookDao.deleteAll()
        bookDao.insert(secondList)
        val result = bookDao.getBooks().getOrAwaitValue()
        assertThat(result).containsAtLeastElementsIn(secondList)
    }
}