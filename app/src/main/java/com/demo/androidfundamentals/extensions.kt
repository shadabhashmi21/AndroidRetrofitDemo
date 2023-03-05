package com.demo.androidfundamentals

import android.util.Log
import com.demo.androidfundamentals.models.MovieModel

fun List<MovieModel>.applyFilterAndSort(
    sortBy: MainActivity.SortBy,
    sortType: MainActivity.SortType,
    filteredList: List<String> = mutableListOf()
): List<MovieModel> {
    val filteredAndSortedMovieList = if (filteredList.isNotEmpty()) {
        filter { filteredList.contains(it.year) }.toMutableList()
    } else {
        this.toMutableList()
    }

    Log.d("filteredAndSortedMovieList", filteredAndSortedMovieList.size.toString())

    if (sortBy == MainActivity.SortBy.Name) {
        if (sortType == MainActivity.SortType.Asc) {
            filteredAndSortedMovieList.sortBy {
                it.title
            }
        } else {
            filteredAndSortedMovieList.sortByDescending {
                it.title
            }
        }
    } else if (sortBy == MainActivity.SortBy.ReleaseDate) {
        if (sortType == MainActivity.SortType.Asc) {
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
