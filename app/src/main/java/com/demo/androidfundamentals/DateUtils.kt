package com.demo.androidfundamentals

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateUtils {
    @SuppressLint("SimpleDateFormat")
    fun getMovieYear(releaseDate: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("yyyy")
        return formatter.format(parser.parse(releaseDate)!!)
    }
}