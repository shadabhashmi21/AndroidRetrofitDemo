package com.demo.androidfundamentals

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.androidfundamentals.adapter.MoviesAdapter
import com.demo.androidfundamentals.databinding.ActivityMainBinding
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val moviesAdapter = MoviesAdapter()
    var apiPageNo = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.adapter = moviesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

       /* viewModel.movieLiveData.observe(this){
            //binding.demoText.text = it.results[0].title
        }*/
        viewModel.apiStatus.observe(this){
           when(it) {
               is MainViewModel.ApiStatus.Success -> {
                   moviesAdapter.populateData(it.apiModel.results.toMutableList())
                   moviesAdapter.notifyDataSetChanged()

               }
               else -> {}
           }
        }
        viewModel.fetchMovieList()
    }

    /*private fun populateData(results: List<MovieModel>) {
        movieList.addAll(results)
        movieAdapter.notifyDataSetChanged()
    }*/
}