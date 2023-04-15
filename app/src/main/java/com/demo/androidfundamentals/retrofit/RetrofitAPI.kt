package com.demo.androidfundamentals.retrofit

import com.demo.androidfundamentals.core.Resource
import com.demo.androidfundamentals.models.APIModel
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("Top250Movies/k_2szq15qf")
    suspend fun getMovies(): Resource<APIModel>
}