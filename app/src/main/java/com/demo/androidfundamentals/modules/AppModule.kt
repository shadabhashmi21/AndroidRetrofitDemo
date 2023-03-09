package com.demo.androidfundamentals.modules

import androidx.room.Room
import com.demo.androidfundamentals.MyApplication.Companion.databaseName
import com.demo.androidfundamentals.database.MovieDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import com.demo.androidfundamentals.source.DataRepository
import com.demo.androidfundamentals.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
    single { DataRepository() }
    single { Room.databaseBuilder(androidContext(), MovieDatabase::class.java, databaseName).build().movieDao() }
}