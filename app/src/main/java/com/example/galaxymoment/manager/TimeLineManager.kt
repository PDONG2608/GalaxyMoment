package com.example.galaxymoment.manager

import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.activity.DetailActivity
import com.example.galaxymoment.activity.GalleryActivity
import com.example.galaxymoment.adapter.TimeLineAdapter
import com.example.galaxymoment.callback.ITouchListener
import com.example.galaxymoment.callback.NavigationFragmentListener
import com.example.galaxymoment.databinding.FragmentTimelineBinding
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.viewholder.TimelineContentViewHolder
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
        Log.i("dongdong", "onClickTimeline position = $position")
        mTimelineViewModel.setCurrentPosPager(position)
        val intent = Intent(mTimelineViewModel.getContext(), DetailActivity::class.java)
        intent.putExtra("timeLineUri", uri)

        val view = (binding.recyclerView.findViewHolderForAdapterPosition(position) as TimelineContentViewHolder).getView()
        val options = ActivityOptionsCompat
            .makeSceneTransitionAnimation(mTimelineViewModel.getContext()!! as GalleryActivity, view, view.transitionName)

        ActivityCompat.startActivity(mTimelineViewModel.getContext()!! as GalleryActivity, intent, options.toBundle())
    }
}