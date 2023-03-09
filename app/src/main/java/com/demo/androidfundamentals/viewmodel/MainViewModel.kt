package com.demo.androidfundamentals.viewmodel

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


    init {
        viewModelScope.launch {
            dataRepository.populateData()
        }
    }
}