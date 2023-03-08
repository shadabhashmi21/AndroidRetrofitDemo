package com.demo.androidfundamentals.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.androidfundamentals.models.MovieModel

@Database(entities = [MovieModel::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}