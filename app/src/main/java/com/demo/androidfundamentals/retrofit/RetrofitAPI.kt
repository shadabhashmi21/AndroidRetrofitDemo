package com.demo.androidfundamentals.retrofit

import com.demo.androidfundamentals.api.MoviesAPI
import com.demo.androidfundamentals.models.APIModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI : MoviesAPI {
    @GET("/3/movie/top_rated?api_key=e3fa29ff3ccecf2807d0ae68d4c4265e")
    override suspend fun getMovies(@Query("page") page: Int): Response<APIModel>
}