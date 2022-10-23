package com.demo.androidfundamentals

import android.util.Log
import com.demo.androidfundamentals.models.MovieModel

fun List<MovieModel>.applyFilterAndSort(sortBy: MainActivity.SortBy, sortType: MainActivity.SortType, filteredList: List<String> = mutableListOf()): List<MovieModel> {
    val filteredAndSortedMovieList = if(filteredList.isNotEmpty()) {
        filter { filteredList.contains(DateUtils.getMovieYear(it.releaseDate)) }.toMutableList()
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
                it.releaseDate
            }
        } else {
            filteredAndSortedMovieList.sortByDescending {
                it.releaseDate
            }
        }
    }
    
    return filteredAndSortedMovieList
}

fun List<MovieModel>.getDistinctMovieYears(): List<String> {
    val movieDates: MutableList<String> = mutableListOf()


    map {
        movieDates.add(DateUtils.getMovieYear(it.releaseDate))
    }
    return movieDates.distinct()
}