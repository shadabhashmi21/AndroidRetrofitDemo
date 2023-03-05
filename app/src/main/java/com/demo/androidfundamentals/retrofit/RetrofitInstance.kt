package com.demo.androidfundamentals.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val builder = Retrofit.Builder()
        .baseUrl("https://imdb-api.com/en/API/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: RetrofitAPI = builder.create(RetrofitAPI::class.java)
}