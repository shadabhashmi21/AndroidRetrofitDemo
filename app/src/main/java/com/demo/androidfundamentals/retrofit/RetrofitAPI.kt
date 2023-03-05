package com.demo.androidfundamentals.retrofit

import com.demo.androidfundamentals.api.MoviesAPI
import com.demo.androidfundamentals.models.APIModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitAPI : MoviesAPI {
    @GET("Top250Movies/k_2szq15qf")
    override suspend fun getMovies(): Response<APIModel>
}