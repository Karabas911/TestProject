package com.example.testproject.di

import androidx.room.Room
import com.example.testproject.Constants
import com.example.testproject.database.AppDatabase
import com.example.testproject.di.AuthModule.authModule
import com.example.testproject.di.ListModule.listModule
import com.example.testproject.network.ApiBookService
import com.example.testproject.network.RetrofitProvider
import com.example.testproject.prefs.PreferenceHelper
import com.example.testproject.prefs.PreferenceWrapper
import com.example.testproject.prefs.PreferenceWrapperImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit

object AppModules {

    private val databaseModule = module {

        single {
            Room.databaseBuilder(
                androidApplication(),
                AppDatabase::class.java,
                Constants.databaseName
            ).build()
        }
    }

    private val prefsModule = module {

        single { PreferenceHelper.customPrefs(androidApplication(), Constants.PREFS_NAME) }

        single<PreferenceWrapper> { PreferenceWrapperImpl(prefs = get()) }
    }

    private val networkModule = module {

        single { RetrofitProvider.buildRetrofit() }

        single { get<Retrofit>().create(ApiBookService::class.java) }
    }

    val appModules = arrayListOf(
        databaseModule,
        networkModule,
        prefsModule,
        listModule,
        authModule
    )
}