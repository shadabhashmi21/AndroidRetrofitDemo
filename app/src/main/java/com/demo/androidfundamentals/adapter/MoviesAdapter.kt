package com.demo.androidfundamentals.adapter

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.androidfundamentals.MainActivity
import com.demo.androidfundamentals.databinding.CardLayoutBinding
import com.demo.androidfundamentals.models.MovieModel
import com.squareup.picasso.Picasso

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val movieList: MutableList<MovieModel> = mutableListOf()

    fun populateData(
        results: List<MovieModel>,
        selectedSortBy: MainActivity.SortBy,
        selectedSortType: MainActivity.SortType
    ) {
        movieList.addAll(results)
        sortData(selectedSortBy, selectedSortType)
    }

    fun getMovies(): List<String> {
        val movieDates: MutableList<String> = mutableListOf()
        movieList.forEach {
            movieDates.add(it.releaseDate.substring(0,4))
        }
        return movieDates.distinct()
    }

    fun sortData(selectedSortBy: MainActivity.SortBy, selectedSortType: MainActivity.SortType) {
        if (movieList.isEmpty()) {
            return
        }
        if (selectedSortBy == MainActivity.SortBy.Name) {
            if (selectedSortType == MainActivity.SortType.Asc) {
                movieList.sortBy {
                    it.title
                }
            } else {
                movieList.sortByDescending {
                    it.title
                }
            }
        } else if (selectedSortBy == MainActivity.SortBy.ReleaseDate) {
            if (selectedSortType == MainActivity.SortType.Asc) {
                movieList.sortBy {
                    it.releaseDate
                }
            } else {
                movieList.sortByDescending {
                    it.releaseDate
                }
            }
        }
        notifyItemRangeChanged(0, movieList.size)
    }


    inner class ViewHolder(binding: CardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val moviePoster: ImageView = binding.moviePoster
        val movieTitle: TextView = binding.movieTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        val imgUrl = "https://image.tmdb.org/t/p/w500/${movie.posterUrl}"
        d("Example", "Name : ${movie.title}")
        holder.movieTitle.text = movie.title
        Picasso.get().load(imgUrl).into(holder.moviePoster)
    }
}