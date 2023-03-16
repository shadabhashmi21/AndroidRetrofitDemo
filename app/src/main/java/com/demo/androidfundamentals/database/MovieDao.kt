package com.demo.androidfundamentals.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demo.androidfundamentals.MainActivity
import com.demo.androidfundamentals.models.MovieModel

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieModel>)

    @Update
    suspend fun updateMovie(movie: MovieModel)

    @Delete
    suspend fun deleteMovie(movie: MovieModel)

    @Query("SELECT * FROM movies ORDER BY (:sortBy) ASC")
    fun getMovies(sortBy: String): List<MovieModel>
}