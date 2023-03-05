package com.demo.androidfundamentals.source

import com.demo.androidfundamentals.api.MoviesAPI
import com.demo.androidfundamentals.models.APIModel
import com.demo.androidfundamentals.models.Model
import retrofit2.Response

class DataRepository(private val moviesAPI: MoviesAPI) {
    suspend fun getMovies(): Response<Model>{
        return moviesAPI.getMovies()
    }
}