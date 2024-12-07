package com.example.galaxymoment.manager

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.galaxymoment.adapter.FilmStripAdapter
import com.example.galaxymoment.databinding.FragmentViewPager2Binding
import com.example.galaxymoment.viewmodel.DetailViewModel

class FilmStripManager(private val mDetailViewModel: DetailViewModel, private val binding: FragmentViewPager2Binding) {
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapterFilmStrip: FilmStripAdapter

    init {
        initView()
    }

    private fun initView() {
        mAdapterFilmStrip = FilmStripAdapter(mDetailViewModel);
        mLayoutManager = LinearLayoutManager(mDetailViewModel.getContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.filmStrip.adapter = mAdapterFilmStrip
        binding.filmStrip.layoutManager = mLayoutManager
    }
}