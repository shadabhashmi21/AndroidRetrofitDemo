package com.demo.androidfundamentals.api

import com.demo.androidfundamentals.models.APIModel

interface MoviesAPI {
    suspend fun getMovies(): APIModel
}