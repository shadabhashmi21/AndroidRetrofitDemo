package com.demo.androidfundamentals

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import com.demo.androidfundamentals.models.MovieModel

object AppUtils {
    @SuppressLint("SimpleDateFormat")
    fun getMovieYear(movieModel: MovieModel): String {
        val parser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy-MM-dd")
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val formatter = SimpleDateFormat("yyyy")
        return formatter.format(parser.parse(movieModel.releaseDate))
    }
}