package com.example.testproject

import android.app.Application
import com.example.testproject.di.AppModules.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@TestApp)
            modules(appModules)
        }
    }
}