package com.demo.androidfundamentals.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.demo.androidfundamentals.models.MovieModel


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: List<MovieModel>)

    @Update
    suspend fun updateMovie(movie: MovieModel)

    @Delete
    suspend fun deleteMovie(movie: MovieModel)

    @RawQuery
    fun getMovies(query: SupportSQLiteQuery): List<MovieModel>

    fun getMovies(sortBy: String, sortType: String): List<MovieModel> = getMovies(
        SimpleSQLiteQuery("SELECT * FROM movies ORDER BY $sortBy $sortType")
    )
}