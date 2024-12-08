package com.example.galaxymoment.manager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.galaxymoment.adapter.FilmStripAdapter
import com.example.galaxymoment.databinding.FragmentDetailBinding
import com.example.galaxymoment.viewmodel.DetailViewModel


class FilmStripManager(private val mDetailViewModel: DetailViewModel, private val binding: FragmentDetailBinding) {
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
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.filmStrip)
    }
}