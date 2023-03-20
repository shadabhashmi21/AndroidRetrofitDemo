package com.demo.androidfundamentals.core

import com.demo.androidfundamentals.models.MovieModel

sealed class Status {
    object Loader : Status()
    data class Error(val message: String) : Status()
    data class Success(val movies: List<MovieModel>) : Status()
}
