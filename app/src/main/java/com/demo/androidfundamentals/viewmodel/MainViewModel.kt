package com.demo.androidfundamentals.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.androidfundamentals.models.APIModel
import com.demo.androidfundamentals.retrofit.RetrofitInstance
import com.demo.androidfundamentals.source.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val dataRepository = DataRepository(RetrofitInstance.service)

    val movieLiveData = MutableLiveData<APIModel>()

    val apiStatus = MutableLiveData<ApiStatus>()

    fun fetchMovieList(){
        apiStatus.value = ApiStatus.Loader
        viewModelScope.launch {
            val movies = withContext(Dispatchers.IO){
                dataRepository.getMovies()
            }
            movieLiveData.value = movies
            apiStatus.value = ApiStatus.Success(movies)
        }
    }

    sealed class ApiStatus {
        object Loader : ApiStatus()
        data class Error(val message: String): ApiStatus()
        data class Success(val movies: APIModel): ApiStatus()
    }
}