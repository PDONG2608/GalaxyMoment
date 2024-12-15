package com.example.galaxymoment.manager

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.adapter.FilmStripAdapter
import com.example.galaxymoment.databinding.ActivityDetailBinding
import com.example.galaxymoment.viewmodel.DetailViewModel


class FilmStripManager(private val mDetailViewModel: DetailViewModel, private val binding: ActivityDetailBinding) {
    private lateinit var snapHelper: LinearSnapHelper
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAdapterFilmStrip: FilmStripAdapter
    private var oldPos: Int = 0

    init {
        initView()
        filmStripListener()
    }

    private fun initView() {
        mAdapterFilmStrip = FilmStripAdapter(mDetailViewModel);
        mLayoutManager = LinearLayoutManager(mDetailViewModel.getContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.filmStrip.adapter = mAdapterFilmStrip
        binding.filmStrip.layoutManager = mLayoutManager
        binding.filmStrip.setRecycledViewPool(RecyclerView.RecycledViewPool())
        binding.filmStrip.itemAnimator = null
        binding.filmStrip.setHasFixedSize(true)
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.filmStrip)
    }

    private fun filmStripListener() {
        binding.filmStrip.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val snapView = snapHelper.findSnapView(mLayoutManager)
                val pos = mLayoutManager.getPosition(snapView!!)
                if(oldPos != pos){
                    mDetailViewModel.setCurrentPosFilmStrip(pos)
                    oldPos = pos
                }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    mDetailViewModel.setFilmStripScrolling(true)
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    mDetailViewModel.setFilmStripScrolling(false)
                }
            }
        })
    }
}