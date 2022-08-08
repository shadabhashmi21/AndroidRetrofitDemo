package com.demo.androidfundamentals.adapter

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

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val movieList: MutableList<MovieModel> = mutableListOf()

    private var selectedSortType = SortType.Asc

    fun populateData(results: List<MovieModel>) {
        movieList.addAll(results)
        loadSortedData()
    }

    fun sort() {
        if(movieList.isEmpty()) {
            return
        }
        toggleSortType()
        loadSortedData()
    }

    private fun toggleSortType() {
        selectedSortType = if(selectedSortType == SortType.Asc) {
            SortType.Desc
        } else {
            SortType.Asc
        }
    }

    private fun loadSortedData() {
        if(selectedSortType == SortType.Asc) {
            movieList.sortBy { it.title }
        } else {
            movieList.sortByDescending { it.title }
        }
        //notifyItemChanged(movieList.size)
        notifyDataSetChanged()
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