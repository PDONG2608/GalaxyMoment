package com.example.galaxymoment.manager

import android.net.Uri
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.galaxymoment.adapter.ViewPagerAdapter
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.FragmentViewPager2Binding
import com.example.galaxymoment.utils.OffsetHelper
import com.example.galaxymoment.utils.Constants
import com.example.galaxymoment.viewmodel.TimelineViewModel

class ViewPagerManager(
    private val mTimelineViewModel: TimelineViewModel,
    private val binding: FragmentViewPager2Binding
) {
    private var adapter: ViewPagerAdapter =
        ViewPagerAdapter(mTimelineViewModel.listItemDetail.value as List<MediaItems>)
    private var mViewPager: ViewPager2 = binding.fragmentViewPager2
    private var oldPosition = -1

    init {
        mViewPager.adapter = adapter
        mViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mViewPager.setCurrentItem(mTimelineViewModel.currentPosPager.value!!, false)
        mViewPager.offscreenPageLimit = 1
        viewPagerListener()
    }

    private fun viewPagerListener() {
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mTimelineViewModel.setCurrentPosPager(position)
                playVideo(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.i("dongdong", "ViewPager2Fragment onPageScrollStateChanged state = $state position = $oldPosition")
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                OffsetHelper.setOffSetForVideo(binding, mTimelineViewModel.currentPosPager.value!!, position, positionOffsetPixels)
                if (OffsetHelper.checkTypeSwipe == Constants.DEFAULT) {
                    Log.i("dongdong", "setURI checkTypeSwipe : SWIPE_TO_RIGHT")
                    oldPosition = position
                    playVideo(position)
                }
            }
        })
    }
    fun playVideo(position: Int) {
        binding.videoView.setVideoURI(Uri.parse(mTimelineViewModel.listItemDetail.value!![position].path))
        binding.videoView.start()
    }

}