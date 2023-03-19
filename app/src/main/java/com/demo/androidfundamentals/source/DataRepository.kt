package com.demo.androidfundamentals.source

import com.demo.androidfundamentals.database.MovieDao
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DataRepository : KoinComponent {
    private val movieDao: MovieDao by inject()

    private val moviesAPI = RetrofitInstance.service

    suspend fun populateData(sortType: String, sortBy: String, filterYears: List<String>): Data {
        val dataInDB = withContext(Dispatchers.IO) {
            movieDao.getMovies(sortBy, sortType, filterYears)
        }
        if (dataInDB.isNotEmpty()) {
            return Data.Success(dataInDB)
        } else {
            // return fetchDatFromWeb()
            return Data.Error("api data not found")
        }
    }

/*    private suspend fun fetchDatFromWeb(): Data {
        val response = withContext(Dispatchers.IO) {
            moviesAPI.getMovies()
        }
        if (response.isSuccessful) {
            data.value = Data.Success(response.body()?.items!!)
            movieDao.insertMovie(response.body()?.items!!)
        } else {
            data.value = Data.Error(response.errorBody().toString())
        }

    }*/
}

sealed class Data {
    object Loader : Data()
    data class Error(val message: String) : Data()
    data class Success(val movies: List<MovieModel>) : Data()
}