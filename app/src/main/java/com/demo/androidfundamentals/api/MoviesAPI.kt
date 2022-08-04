package com.demo.androidfundamentals.api

import com.demo.androidfundamentals.models.APIModel
import retrofit2.Response

interface MoviesAPI {
    suspend fun getMovies(): Response<APIModel>
}