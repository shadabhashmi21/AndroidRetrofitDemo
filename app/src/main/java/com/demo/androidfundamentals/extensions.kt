package com.demo.androidfundamentals

import android.util.Log
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.viewmodel.MainViewModel
import com.demo.androidfundamentals.viewmodel.SortBy
import com.demo.androidfundamentals.viewmodel.SortType

fun List<MovieModel>.getDistinctMovieYears(): List<String> {

    return map {
        it.year
    }.distinct()
}
