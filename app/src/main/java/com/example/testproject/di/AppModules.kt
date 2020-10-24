package com.example.testproject.di

import androidx.room.Room
import com.example.testproject.Constants
import com.example.testproject.database.AppDatabase
import com.example.testproject.di.AuthModule.authModule
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object AppModules {

    val appModules = arrayListOf(
        authModule
    )

    val databaseModule = module {

        single {
            Room.databaseBuilder(
                androidApplication(),
                AppDatabase::class.java,
                Constants.databaseName
            ).build()
        }
    }
}