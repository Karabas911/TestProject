package com.example.testproject.di

import com.example.testproject.ui.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ListModule {

    val listModule = module {

        viewModel { ListViewModel(prefs = get(), authRepository = get()) }
    }
}