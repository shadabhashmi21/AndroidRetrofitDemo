package com.demo.androidfundamentals.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val rating: String,
    val image: String,
    val rank: String,
    val year: String
)
