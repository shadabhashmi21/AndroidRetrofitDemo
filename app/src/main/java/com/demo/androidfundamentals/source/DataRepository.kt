package com.demo.androidfundamentals.source

import com.demo.androidfundamentals.core.Resource
import com.demo.androidfundamentals.core.Status
import com.demo.androidfundamentals.database.MovieDao
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.retrofit.RetrofitInstance
import com.demo.androidfundamentals.utils.InternetUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DataRepository : KoinComponent {
    private val movieDao: MovieDao by inject()
    private val internetUtils: InternetUtils by inject()

    private val moviesAPI = RetrofitInstance.service

    suspend fun populateData(
        sortType: String,
        sortBy: String,
        filterYears: List<String>,
        fetchFromWeb: Boolean = false
    ): Resource<List<MovieModel>> {
        var dataInDB = fetchLocalData(sortBy, sortType, filterYears)
        return try {
            if((fetchFromWeb || dataInDB.isEmpty()) && !internetUtils.isOnline()) {
                throw Exception("Internet not found")
            }
            syncDataFromWeb()
            dataInDB = fetchLocalData(sortBy, sortType, filterYears)
            if (dataInDB.isEmpty()) {
                throw Exception("unable to get data")
            }
            Resource.success(dataInDB)
        } catch (e: Exception) {
            Resource.error(e.message)
        }
    }

    private suspend fun fetchLocalData(
        sortBy: String,
        sortType: String,
        filterYears: List<String>
    ) = withContext(Dispatchers.IO) {
        movieDao.getMovies(sortBy = sortBy, sortType = sortType, filterYears = filterYears)
    }

    private suspend fun syncDataFromWeb() {
        val apiData = withContext(Dispatchers.IO) {
            moviesAPI.getMovies()
        }
        if (apiData.status == Status.SUCCESS && apiData.data != null && apiData.data!!.isNotEmpty()) {
            // todo - remove/replace old data
            movieDao.insertMovies(apiData.data!!)
        }
    }
}