package com.demo.androidfundamentals.models

data class APIModel(
    val errorMessage: String,
    val items: List<MovieModel>
)
