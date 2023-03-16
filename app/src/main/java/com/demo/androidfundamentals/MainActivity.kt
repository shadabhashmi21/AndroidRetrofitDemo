package com.demo.androidfundamentals

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.androidfundamentals.adapter.MoviesAdapter
import com.demo.androidfundamentals.databinding.ActivityMainBinding
import com.demo.androidfundamentals.databinding.FilterBottomSheetBinding
import com.demo.androidfundamentals.databinding.SortBottomSheetBinding
import com.demo.androidfundamentals.models.MovieModel
import com.demo.androidfundamentals.source.DataRepository
import com.demo.androidfundamentals.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val moviesAdapter = MoviesAdapter()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var sortBottomSheetBinding: SortBottomSheetBinding
    private lateinit var sortDialog: BottomSheetDialog
    private lateinit var filterBottomSheetBinding: FilterBottomSheetBinding
    private lateinit var filterDialog: BottomSheetDialog
    private var moviesList = listOf<MovieModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gridLayoutManager = GridLayoutManager(this@MainActivity, 2)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.adapter = moviesAdapter
        binding.recyclerView.layoutManager = gridLayoutManager

        viewModel.repositoryState.observe(this) {
            when (it) {
                is DataRepository.RepositoryState.Success -> {
                    moviesList = it.movies
                    //loadFilteredAndSortedMovies(selectedSortBy, selectedSortType)
                    moviesAdapter.populateData(moviesList)
                    binding.progressBar.visibility = View.GONE
                    initSortBottomSheet()
                    initFilterBottomSheet()
                }
                is DataRepository.RepositoryState.Loader -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {}
            }
        }

        binding.sortBtn.setOnClickListener {
            sortDialog.show()
        }

        binding.filterBtn.setOnClickListener {
            filterDialog.show()
        }
    }

    private fun initFilterBottomSheet() {
        filterDialog = BottomSheetDialog(this)
        filterBottomSheetBinding = FilterBottomSheetBinding.inflate(layoutInflater)
        filterDialog.setContentView(filterBottomSheetBinding.root)
        val chipGroup = filterBottomSheetBinding.chipGroup

        moviesList.getDistinctMovieYears().forEach {
            chipGroup.addView(createTagChip(this, it))
        }

        filterBottomSheetBinding.applyButton.setOnClickListener {
            val filterList = chipGroup.checkedChipIds.map { id ->
                chipGroup.findViewById<Chip>(id).text.toString()
            }
            //loadFilteredAndSortedMovies(viewModel.selectedSortBy, viewModel.selectedSortType, filterList)
            filterDialog.dismiss()
            Log.d("filterList", filterList.toString())
        }
    }

    private fun initSortBottomSheet() {
        sortDialog = BottomSheetDialog(this)
        sortBottomSheetBinding = SortBottomSheetBinding.inflate(layoutInflater)
        sortBottomSheetBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
          /*  viewModel.selectedSortBy =
                when (checkedId) {
                    sortBottomSheetBinding.byRating.id -> MainViewModel.SortBy.Rating
                    sortBottomSheetBinding.byName.id -> MainViewModel.SortBy.Name
                    else -> MainViewModel.SortBy.ReleaseDate
                }*/

            //loadSortByData(viewModel.selectedSortBy)
            //sortDialog.dismiss()
        }

        sortBottomSheetBinding.toggleButton.setOnClickListener {
            viewModel.selectedSortType = if (viewModel.selectedSortType == MainViewModel.SortType.ASC) MainViewModel.SortType.DESC else MainViewModel.SortType.ASC
            //loadSortTypeData(viewModel.selectedSortType)
            sortDialog.dismiss()
        }

        // load default data
       /* when (viewModel.selectedSortBy) {
            MainViewModel.SortBy.Rating -> {
                sortBottomSheetBinding.byRating.isChecked = true
            }
            MainViewModel.SortBy.Name -> {
                sortBottomSheetBinding.byName.isChecked = true
            }
            else -> {
                sortBottomSheetBinding.byReleaseDate.isChecked = true
            }
        }*/
        //loadSortTypeData(viewModel.selectedSortType)
        //

        sortDialog.setContentView(sortBottomSheetBinding.root)
    }

    /*private fun loadFilteredAndSortedMovies(sortBy: SortBy, sortType: SortType, filteredList: List<String> = mutableListOf()) {
        val filteredAndSortedMovies = moviesList.applyFilterAndSort(sortBy, sortType, filteredList)
        moviesAdapter.populateData(filteredAndSortedMovies)
    }*/

    /*private fun loadSortByData(sortBy: SortBy) {
        if (sortBy == SortBy.Name) {
            Toast.makeText(this, "By name clicked", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "By release date clicked", Toast.LENGTH_SHORT).show()
        }
        loadFilteredAndSortedMovies(selectedSortBy, selectedSortType)
    }*/

    /*private fun loadSortTypeData(sortType: SortType) {
        if (sortType == SortType.ASC) {
            sortBottomSheetBinding.toggleButton.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
        } else {
            sortBottomSheetBinding.toggleButton.setImageResource(R.drawable.ic_baseline_arrow_downward_24)
        }
        loadFilteredAndSortedMovies(selectedSortBy, selectedSortType)
    }*/

    private fun createTagChip(context: Context, chipName: String): Chip {
        return Chip(context).apply {
            text = chipName
            isCheckable = true
        }
    }
}