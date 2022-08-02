package com.demo.androidfundamentals

import com.demo.androidfundamentals.models.ApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/3/movie/top_rated?api_key=7a21e3b33f92fe9700af60427bb7ca24")
    fun getMovieDetails(@Query("page") page: Int): Call<ApiModel>
}