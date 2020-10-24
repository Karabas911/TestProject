package com.example.testproject.di

import com.example.testproject.repo.AuthRepository
import com.example.testproject.repo.AuthRepositoryImpl
import com.example.testproject.ui.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AuthModule {

    val authModule = module {

        single<AuthRepository> { AuthRepositoryImpl() }

        viewModel { SignUpViewModel(get()) }
    }
}