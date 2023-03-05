package com.demo.androidfundamentals.models

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("title") val title: String,
    @SerializedName("image") val posterUrl: String,
    @SerializedName("imDbRating") val releaseDate: String
)
