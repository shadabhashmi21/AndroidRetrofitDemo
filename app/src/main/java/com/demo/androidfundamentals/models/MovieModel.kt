package com.demo.androidfundamentals.models

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterUrl: String,
    @SerializedName("release_date") val releaseDate: String
)
