package com.demo.androidfundamentals.retrofit

import com.demo.androidfundamentals.api.MoviesAPI
import com.demo.androidfundamentals.models.APIModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitAPI : MoviesAPI {
    @GET("/3/movie/top_rated?api_key=7a21e3b33f92fe9700af60427bb7ca24")
    override suspend fun getMovies(): Response<APIModel>
}