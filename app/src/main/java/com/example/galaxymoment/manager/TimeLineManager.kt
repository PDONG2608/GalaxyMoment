package com.example.galaxymoment.manager

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.R
import com.example.galaxymoment.activity.GalleryActivity
import com.example.galaxymoment.adapter.TimeLineAdapter
import com.example.galaxymoment.callback.ITouchListener
import com.example.galaxymoment.callback.NavigationFragmentListener
import com.example.galaxymoment.databinding.FragmentTimelineBinding
import com.example.galaxymoment.fragment.DetailFragment
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.viewmodel.TimelineViewModel

class TimeLineManager (
    private val mTimelineViewModel: TimelineViewModel,
    private val binding: FragmentTimelineBinding
) : ITouchListener {
    private lateinit var mLayoutManager: GridLayoutManager
    private lateinit var mTimelineAdapter: TimeLineAdapter
    private lateinit var mNavigationFragmentListener: NavigationFragmentListener

    init {
        initView()
    }

    private fun initView() {
        mTimelineAdapter = TimeLineAdapter(mTimelineViewModel)
        mTimelineAdapter.setITouchListener(this)
        mLayoutManager = GridLayoutManager(mTimelineViewModel.getContext(), 4)
        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.adapter = mTimelineAdapter
        LogicUtils.setSpanSize(mTimelineAdapter, mLayoutManager, 4)
    }

    override fun onClickTimeline(uri: String, position: Int) {
//        mTimelineViewModel.setCurrentPosPager(position)
        val detailFragment = DetailFragment()
        val bundle = Bundle()
        bundle.putString("timeLineUri", uri)
        detailFragment.arguments = bundle
        (mTimelineViewModel.getContext() as GalleryActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.timeLineLayout, detailFragment)
            .addToBackStack(null)
            .commit()
    }
}