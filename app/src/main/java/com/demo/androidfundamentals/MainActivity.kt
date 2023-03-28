package com.demo.androidfundamentals

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.androidfundamentals.adapter.MoviesAdapter
import com.demo.androidfundamentals.databinding.ActivityMainBinding
import com.demo.androidfundamentals.databinding.FilterBottomSheetBinding
import com.demo.androidfundamentals.databinding.SortBottomSheetBinding
import com.demo.androidfundamentals.core.Status
import com.demo.androidfundamentals.viewmodel.MainViewModel
import com.demo.androidfundamentals.viewmodel.SortBy
import com.demo.androidfundamentals.viewmodel.SortType
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gridLayoutManager = GridLayoutManager(this@MainActivity, 2)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.recyclerView.adapter = moviesAdapter
        binding.recyclerView.layoutManager = gridLayoutManager

        initSortBottomSheet()

        viewModel.data.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    moviesAdapter.populateData(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    initFilterBottomSheet()
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.retryBtn.visibility = View.VISIBLE
                    binding.retryBtn.setOnClickListener {
                        viewModel.populateData()
                    }
                }
            }
        }

        binding.sortBtn.setOnClickListener {
            sortDialog.show()
        }

        binding.filterBtn.setOnClickListener {
            filterDialog.show()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.populateData(true)

            binding.swipeRefreshLayout.isRefreshing = false
            Toast.makeText(applicationContext, "Page Refreshed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initFilterBottomSheet() {
        filterDialog = BottomSheetDialog(this)
        filterBottomSheetBinding = FilterBottomSheetBinding.inflate(layoutInflater)
        filterDialog.setContentView(filterBottomSheetBinding.root)
        val chipGroup = filterBottomSheetBinding.chipGroup

        viewModel.availableYears.forEach {
            chipGroup.addView(createTagChip(this, it))
        }

        filterBottomSheetBinding.applyButton.setOnClickListener {
            viewModel.filterYears = chipGroup.checkedChipIds.map { id ->
                chipGroup.findViewById<Chip>(id).text.toString()
            } as MutableList<String>
            viewModel.populateData()
            filterDialog.dismiss()
        }
    }

    private fun initSortBottomSheet() {
        sortDialog = BottomSheetDialog(this)
        sortBottomSheetBinding = SortBottomSheetBinding.inflate(layoutInflater)
        sortBottomSheetBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectedSortBy =
                when (checkedId) {
                    sortBottomSheetBinding.byRating.id -> SortBy.imDbRating
                    sortBottomSheetBinding.byName.id -> SortBy.title
                    else -> SortBy.year
                }

            viewModel.populateData()
            sortDialog.dismiss()
        }

        sortBottomSheetBinding.toggleButton.setOnClickListener {
            viewModel.selectedSortType =
                if (viewModel.selectedSortType == SortType.ASC) SortType.DESC else SortType.ASC
            toggleSortIcon()
            viewModel.populateData()
            sortDialog.dismiss()
        }

        // load default data
        when (viewModel.selectedSortBy) {
            SortBy.imDbRating -> sortBottomSheetBinding.byRating.isChecked = true
            SortBy.title -> sortBottomSheetBinding.byName.isChecked = true
            else -> sortBottomSheetBinding.byReleaseDate.isChecked = true
        }

        sortDialog.setContentView(sortBottomSheetBinding.root)
    }

    private fun toggleSortIcon() {
        if (viewModel.selectedSortType == SortType.ASC) {
            sortBottomSheetBinding.toggleButton.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
        } else {
            sortBottomSheetBinding.toggleButton.setImageResource(R.drawable.ic_baseline_arrow_downward_24)
        }
    }

    private fun createTagChip(context: Context, chipName: String): Chip {
        return Chip(context).apply {
            text = chipName
            isCheckable = true
        }
    }
}