package com.example.testproject.di

import com.example.testproject.database.AppDatabase
import com.example.testproject.repo.AuthRepository
import com.example.testproject.repo.AuthRepositoryImpl
import com.example.testproject.ui.SignInViewModel
import com.example.testproject.ui.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AuthModule {

    val authModule = module {

        single { get<AppDatabase>().userDao() }

        single<AuthRepository> { AuthRepositoryImpl(userDao = get()) }

        viewModel { SignUpViewModel(repository = get(), prefs = get()) }

        viewModel { SignInViewModel(repository = get(), prefs = get()) }

    }
}