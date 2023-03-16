package com.demo.androidfundamentals

import android.util.Log
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.viewmodel.MainViewModel

fun List<MovieModel>.applyFilterAndSort(
    sortBy: MainViewModel.SortBy,
    sortType: MainViewModel.SortType,
    filteredList: List<String> = mutableListOf()
): List<MovieModel> {
    val filteredAndSortedMovieList = if (filteredList.isNotEmpty()) {
        filter { filteredList.contains(it.year) }.toMutableList()
    } else {
        this.toMutableList()
    }

    Log.d("filteredAndSortedMovieList", filteredAndSortedMovieList.size.toString())

    if (sortBy == MainViewModel.SortBy.title) {
        if (sortType == MainViewModel.SortType.ASC) {
            filteredAndSortedMovieList.sortBy {
                it.title
            }
        } else {
            filteredAndSortedMovieList.sortByDescending {
                it.title
            }
        }
    } else if (sortBy == MainViewModel.SortBy.year) {
        if (sortType == MainViewModel.SortType.ASC) {
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
