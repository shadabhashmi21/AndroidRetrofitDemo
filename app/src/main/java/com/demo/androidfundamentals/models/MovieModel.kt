package com.demo.androidfundamentals.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieModel(
    @PrimaryKey
    @SerializedName("id") val id: String,
    @SerializedName("crew") val crew: String,
    @SerializedName("fullTitle") val fullTitle: String,
    @SerializedName("imDbRating") val imDbRating: String,
    @SerializedName("imDbRatingCount") val imDbRatingCount: String,
    @SerializedName("image") val image: String,
    @SerializedName("rank") val rank: String,
    @SerializedName("title") val title: String,
    @SerializedName("year") val year: String
)
