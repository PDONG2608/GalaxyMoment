package com.example.galaxymoment.manager

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.galaxymoment.adapter.ViewPagerAdapter
import com.example.galaxymoment.databinding.FragmentDetailBinding
import com.example.galaxymoment.utils.OffsetHelper
import com.example.galaxymoment.utils.Constants
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.viewmodel.DetailViewModel

class ViewPagerManager(
    private val mDetailViewModel: DetailViewModel,
    private val binding: FragmentDetailBinding,
    private val arguments: Bundle?
) {
    private lateinit var mTimelineUri: String
    private lateinit var adapter: ViewPagerAdapter
    private var mViewPager: ViewPager2 = binding.viewpager
    private var oldPosition = -1
    private lateinit var mDetailViewStub: MoreInfoManager

    init {
        arguments?.let {
            mTimelineUri = it.getString("timeLineUri").toString()
        }
        val positionOpenDetailView = LogicUtils.calculatePositionOpenDetail(mDetailViewModel,mTimelineUri)
        Log.i("dongdong", "ViewPagerManager positionOpenDetailView = $positionOpenDetailView")
        adapter = ViewPagerAdapter(mDetailViewModel.getListItemDetail())
        mViewPager.adapter = adapter
        mViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mViewPager.setCurrentItem(positionOpenDetailView, false)
        mViewPager.offscreenPageLimit = 1
        viewPagerListener()
    }

    private fun viewPagerListener() {
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.i("dongdong", "ViewPager2Fragment onPageSelected")
                super.onPageSelected(position)
                mDetailViewStub = MoreInfoManager(mDetailViewModel, binding, position)
                mDetailViewModel.setCurrentPosPager(position)
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
                OffsetHelper.setOffSetForVideo(binding, mDetailViewModel.currentPosPager.value!!, position, positionOffsetPixels)
                if (OffsetHelper.checkTypeSwipe == Constants.DEFAULT) {
                    Log.i("dongdong", "setURI checkTypeSwipe : SWIPE_TO_RIGHT")
                    oldPosition = position
                    playVideo(position)
                }
            }
        })
    }
    fun playVideo(position: Int) {
        binding.videoView.setVideoURI(Uri.parse(mDetailViewModel.listItemDetail.value!![position].uri))
        binding.videoView.start()
    }

}