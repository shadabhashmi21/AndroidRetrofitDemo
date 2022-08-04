package com.demo.androidfundamentals.source

import com.demo.androidfundamentals.api.MoviesAPI
import com.demo.androidfundamentals.models.APIModel
import retrofit2.Response

class DataRepository(private val moviesAPI: MoviesAPI) {
    suspend fun getMovies(): Response<APIModel>{
        return moviesAPI.getMovies()
    }
}