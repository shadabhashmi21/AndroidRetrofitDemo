package com.demo.androidfundamentals.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.androidfundamentals.source.DataRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val dataRepository: DataRepository by inject()
    val repositoryState: LiveData<DataRepository.RepositoryState> = dataRepository.repositoryState

    var selectedSortType = SortType.DESC
    var selectedSortBy = SortBy.imDbRating


    init {
        viewModelScope.launch {
            dataRepository.populateData(sortType = selectedSortType.toString(), sortBy = selectedSortBy.toString())
        }
    }

    fun loadSortByData() {
        viewModelScope.launch {
            dataRepository.populateData(selectedSortType.toString(), selectedSortBy.toString())
        }
    }
}

enum class SortType {
    ASC, DESC
}

enum class SortBy {
    imDbRating, title, year
}