package com.demo.androidfundamentals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.androidfundamentals.getDistinctMovieYears
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.source.Data
import com.demo.androidfundamentals.source.DataRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val dataRepository: DataRepository by inject()
    val data = MutableLiveData<Data>()

    var selectedSortType = SortType.DESC
    var selectedSortBy = SortBy.imDbRating
    var filterYears = mutableListOf<String>()
    var availableYears = mutableListOf<String>()


    init {
        populateData()
    }

    fun populateData() {
        data.value = Data.Loader
        viewModelScope.launch {
            val data = dataRepository.populateData(sortType = selectedSortType.toString(), sortBy = selectedSortBy.toString(), filterYears)
            if(data is Data.Success && filterYears.isEmpty()) {
                availableYears = data.movies.getDistinctMovieYears() as MutableList<String>
            }
            this@MainViewModel.data.value = data
        }
    }
}

enum class SortType {
    ASC, DESC
}

enum class SortBy {
    imDbRating, title, year
}