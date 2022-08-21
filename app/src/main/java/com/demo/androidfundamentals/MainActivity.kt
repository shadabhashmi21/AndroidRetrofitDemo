package com.demo.androidfundamentals

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.androidfundamentals.adapter.MoviesAdapter
import com.demo.androidfundamentals.databinding.ActivityMainBinding
import com.demo.androidfundamentals.databinding.SortBottomSheetBinding
import com.demo.androidfundamentals.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val moviesAdapter = MoviesAdapter()
    lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var bottomSheetBinding: SortBottomSheetBinding
    private lateinit var dialog: BottomSheetDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gridLayoutManager = GridLayoutManager(this@MainActivity, 2)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.adapter = moviesAdapter
        binding.recyclerView.layoutManager = gridLayoutManager

        viewModel.apiStatus.observe(this) {
            when (it) {

                is MainViewModel.ApiStatus.Success -> {
                    moviesAdapter.populateData(it.apiModel.results.toMutableList())
                    binding.progressBar.visibility = View.GONE
                }
                is MainViewModel.ApiStatus.Loader -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
        viewModel.fetchMovieList()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchMovieList(true)
                }
            }
        })

        var isSortByNameSelected = true
        binding.sortBtn.setOnClickListener {
            dialog = BottomSheetDialog(this)
            bottomSheetBinding = SortBottomSheetBinding.inflate(layoutInflater)
            dialog.setContentView(bottomSheetBinding.root)
            dialog.show()
            bottomSheetBinding.byName.isChecked = isSortByNameSelected
            bottomSheetBinding.byReleaseDate.isChecked = !isSortByNameSelected

            bottomSheetBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

                if (checkedId == bottomSheetBinding.byName.id) {
                    Toast.makeText(this, "By name clicked", Toast.LENGTH_SHORT).show()
                    moviesAdapter.sortByName()
                } else if (checkedId == bottomSheetBinding.byReleaseDate.id) {
                    Toast.makeText(this, "By release date clicked", Toast.LENGTH_SHORT).show()
                    isSortByNameSelected = isSortByNameSelected.not()
                    moviesAdapter.sortByDate()
                }
                dialog.dismiss()
            }
        }
    }
}