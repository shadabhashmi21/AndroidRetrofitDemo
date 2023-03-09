package com.demo.androidfundamentals.source

import androidx.lifecycle.MutableLiveData
import com.demo.androidfundamentals.database.MovieDao
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DataRepository() : KoinComponent {
    private val movieDao: MovieDao by inject()

    private val moviesAPI = RetrofitInstance.service
    private val repositoryState = MutableLiveData<RepositoryState>()

    suspend fun loadData() {
        repositoryState.value = RepositoryState.Loader
        val dataInDB = movieDao.getMovies()
        if (dataInDB.isNotEmpty()) {
            repositoryState.value = RepositoryState.Success(dataInDB)
        } else {
            fetchDatFromWeb()
        }
    }

    private suspend fun fetchDatFromWeb() {
        repositoryState.value = RepositoryState.Loader
        val response = withContext(Dispatchers.IO) {
            moviesAPI.getMovies()
        }
        if (response.isSuccessful) {
            repositoryState.value = RepositoryState.Success(response.body()?.items!!)
        } else {
            repositoryState.value = RepositoryState.Error(response.errorBody().toString())
        }

    }

    sealed class RepositoryState {
        object Loader : RepositoryState()
        data class Error(val message: String) : RepositoryState()
        data class Success(val movies: List<MovieModel>) : RepositoryState()
    }
}