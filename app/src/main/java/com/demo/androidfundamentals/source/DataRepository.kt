package com.demo.androidfundamentals.source

import com.demo.androidfundamentals.core.Status
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

    suspend fun populateData(sortType: String, sortBy: String, filterYears: List<String>): Status {
        val dataInDB = withContext(Dispatchers.IO) {
            movieDao.getMovies(sortBy, sortType, filterYears)
        }
        return if (dataInDB.isNotEmpty()) {
            Status.Success(dataInDB)
        } else {
            // return fetchDatFromWeb()
            Status.Error("api data not found")
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