package com.demo.androidfundamentals

import android.app.Application
import com.demo.androidfundamentals.modules.appModule
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {

    companion object {
        const val databaseName = "MOVIE_DB"
    }

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() = org.koin.core.context.startKoin {
        androidContext(this@MyApplication)
        modules(appModule)
    }
}