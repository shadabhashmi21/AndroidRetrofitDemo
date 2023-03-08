package com.demo.androidfundamentals.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demo.androidfundamentals.models.MovieModel

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieModel)

    @Update
    suspend fun updateMovie(movie: MovieModel)

    @Delete
    suspend fun deleteMovie(movie: MovieModel)

    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<MovieModel>>
}