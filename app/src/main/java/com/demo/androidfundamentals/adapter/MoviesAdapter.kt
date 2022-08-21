package com.demo.androidfundamentals.adapter

import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.androidfundamentals.databinding.CardLayoutBinding
import com.demo.androidfundamentals.models.MovieModel
import com.squareup.picasso.Picasso

enum class SortType {
    Asc, Desc
}

enum class SortBy{
    Name, ReleaseDate
}

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val movieList: MutableList<MovieModel> = mutableListOf()

    private var selectedSortType = SortType.Asc
    private var selectedSortBy = SortBy.Name

    fun populateData(results: List<MovieModel>) {
        movieList.addAll(results)
        if (selectedSortBy == SortBy.Name){
            sortByName()
        }else{
            sortByDate()
        }
    }

    fun sortByName(){
        selectedSortBy = SortBy.Name
        if(movieList.isEmpty()) {
            return
        }
        if (selectedSortType == SortType.Asc){
            movieList.sortBy {
                it.title
            }
        }else{
            movieList.sortByDescending {
                it.title
            }
        }
        notifyItemRangeChanged(0, movieList.size)
    }

    fun sortByDate(){
        selectedSortBy = SortBy.ReleaseDate
        if(movieList.isEmpty()) {
            return
        }
        if (selectedSortType == SortType.Asc){
            movieList.sortBy {
                it.releaseDate
            }
        }else{
            movieList.sortByDescending {
                it.releaseDate
            }
        }
        notifyItemRangeChanged(0, movieList.size)
        Log.d("dates", movieList.map { it.releaseDate }.toList().toString());
    }

    private fun toggleSortType() {
        selectedSortType = if(selectedSortType == SortType.Asc) {
            SortType.Desc
        } else {
            SortType.Asc
        }
    }

    inner class ViewHolder(binding: CardLayoutBinding): RecyclerView.ViewHolder(binding.root){
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