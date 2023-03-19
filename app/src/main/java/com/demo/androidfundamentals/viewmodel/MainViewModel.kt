package com.demo.androidfundamentals.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    init {
        populateData()
    }

    fun populateData() {
        viewModelScope.launch {
            data.value = dataRepository.populateData(sortType = selectedSortType.toString(), sortBy = selectedSortBy.toString())
        }
    }
}

enum class SortType {
    ASC, DESC
}

enum class SortBy {
    imDbRating, title, year
}