package com.demo.androidfundamentals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.androidfundamentals.databinding.ActivityMainBinding
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityMainBinding

    //ViewModel
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.movieLiveData.observe(this){
            binding.demoText.text = it.results[0].title
        }
        viewModel.fetchMovieList()
    }
}