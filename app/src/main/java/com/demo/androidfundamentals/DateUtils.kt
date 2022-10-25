package com.demo.androidfundamentals

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getMovieYear(releaseDate: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formatter = SimpleDateFormat("yyyy", Locale.US)
        return formatter.format(parser.parse(releaseDate)!!)
    }
}