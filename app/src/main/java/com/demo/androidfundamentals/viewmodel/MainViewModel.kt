package com.demo.androidfundamentals.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.androidfundamentals.core.Resource
import com.demo.androidfundamentals.core.Status
import com.demo.androidfundamentals.getDistinctMovieYears
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.source.DataRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val dataRepository: DataRepository by inject()
    val data = MutableLiveData<Resource<List<MovieModel>>>()

    var selectedSortType = SortType.DESC
    var selectedSortBy = SortBy.imDbRating
    var filterYears = mutableListOf<String>()
    var availableYears = mutableListOf<String>()


    init {
        populateData()
    }

    fun populateData(isPageRefreshed: Boolean = false) {
        data.value = Resource.loading()
        viewModelScope.launch {
            val data = if (isPageRefreshed) dataRepository.populateData(
                SortType.DESC.toString(),
                SortBy.imDbRating.toString(),
                emptyList(),
                true
            )
            else
                dataRepository.populateData(
                    sortType = selectedSortType.toString(),
                    sortBy = selectedSortBy.toString(),
                    filterYears
                )
            if (data.status == Status.SUCCESS && filterYears.isEmpty()) {
                availableYears = data.data?.getDistinctMovieYears() as MutableList<String>
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