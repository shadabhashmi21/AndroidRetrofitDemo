package com.demo.androidfundamentals.database

import androidx.sqlite.db.SimpleSQLiteQuery
import com.demo.androidfundamentals.models.MovieModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MovieDbHelper: KoinComponent {
    private val movieDao: MovieDao by inject()

   
}