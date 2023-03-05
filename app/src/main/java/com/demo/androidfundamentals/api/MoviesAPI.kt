package com.demo.androidfundamentals.api

import com.demo.androidfundamentals.models.APIModel
import com.demo.androidfundamentals.models.Model
import retrofit2.Response

interface MoviesAPI {
    suspend fun getMovies(): Response<Model>
}