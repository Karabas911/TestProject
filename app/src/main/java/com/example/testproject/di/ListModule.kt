package com.example.testproject.di

import com.example.testproject.repo.BookRepository
import com.example.testproject.repo.BookRepositoryImpl
import com.example.testproject.ui.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ListModule {

    val listModule = module {

        single<BookRepository> { BookRepositoryImpl(apiBookService = get()) }

        viewModel {
            ListViewModel(
                prefs = get(),
                authRepository = get(),
                bookRepository = get()
            )
        }
    }
}