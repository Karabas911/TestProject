package com.example.testproject.di

import com.example.testproject.database.AppDatabase
import com.example.testproject.repo.BookRepository
import com.example.testproject.repo.BookRepositoryImpl
import com.example.testproject.ui.ListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ListModule {

    val listModule = module {

        single { get<AppDatabase>().bookDao() }

        single<BookRepository> { BookRepositoryImpl(apiBookService = get(), bookDao = get()) }

        viewModel {
            ListViewModel(
                app = androidApplication(),
                prefs = get(),
                authRepository = get(),
                bookRepository = get()
            )
        }
    }
}