package com.demo.androidfundamentals.source

import com.demo.androidfundamentals.api.MoviesAPI
import com.demo.androidfundamentals.models.APIModel

class DataRepository(private val moviesAPI: MoviesAPI) {
    suspend fun getMovies(): APIModel{
        return moviesAPI.getMovies()
    }
}