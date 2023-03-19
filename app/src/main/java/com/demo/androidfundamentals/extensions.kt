package com.demo.androidfundamentals

import android.util.Log
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.viewmodel.MainViewModel
import com.demo.androidfundamentals.viewmodel.SortBy
import com.demo.androidfundamentals.viewmodel.SortType

fun List<MovieModel>.applyFilterAndSort(
    sortBy: SortBy,
    sortType: SortType,
    filteredList: List<String> = mutableListOf()
): List<MovieModel> {
    val filteredAndSortedMovieList = if (filteredList.isNotEmpty()) {
        filter { filteredList.contains(it.year) }.toMutableList()
    } else {
        this.toMutableList()
    }

    Log.d("filteredAndSortedMovieList", filteredAndSortedMovieList.size.toString())

    if (sortBy == SortBy.title) {
        if (sortType == SortType.ASC) {
            filteredAndSortedMovieList.sortBy {
                it.title
            }
        } else {
            filteredAndSortedMovieList.sortByDescending {
                it.title
            }
        }
    } else if (sortBy == SortBy.year) {
        if (sortType == SortType.ASC) {
            filteredAndSortedMovieList.sortBy {
                it.year
            }
        } else {
            filteredAndSortedMovieList.sortByDescending {
                it.year
            }
        }
    }

    return filteredAndSortedMovieList
}

fun List<MovieModel>.getDistinctMovieYears(): List<String> {

    return map {
        it.year
    }.distinct()
}
