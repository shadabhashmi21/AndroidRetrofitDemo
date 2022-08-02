package com.demo.androidfundamentals.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val builder = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: RetrofitAPI = builder.create(RetrofitAPI::class.java)
}