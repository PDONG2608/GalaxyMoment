package com.example.galaxymoment.manager

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.galaxymoment.adapter.ViewPagerAdapter
import com.example.galaxymoment.databinding.FragmentDetailBinding
import com.example.galaxymoment.utils.AnimationHelper
import com.example.galaxymoment.utils.OffsetHelper
import com.example.galaxymoment.utils.Constants
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.utils.OnSwipeTouchListener
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
    private var isShowMoreInfo = false

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
        buttonMoreInfoListener()
        swipeUpDownVideo()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun swipeUpDownVideo() {
        binding.viewpager.setOnTouchListener(object : OnSwipeTouchListener( binding.viewpager.context) {
            override fun onSwipeTop() {
                Log.i("dongdong", "onSwipeTop")
                showMoreInfo()
            }

            override fun onSwipeBottom() {
                Log.i("dongdong", "onSwipeBottom")
                hideMoreInfo()
            }
            override fun onSwipeRight() {
                Log.i("dongdong", "onSwipeRight")
            }
            override fun onSwipeLeft() {
                Log.i("dongdong", "onSwipeLeft")
            }
        })
    }

    private fun buttonMoreInfoListener() {
        binding.buttonOpenMoreinfo.setOnClickListener {
            isShowMoreInfo = !isShowMoreInfo
            if (isShowMoreInfo) showMoreInfo() else hideMoreInfo()
        }
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
        binding.videoView.setVideoURI(mDetailViewModel.listItemDetail.value!![position].uri)
        binding.videoView.start()
    }

    private fun showMoreInfo() {
        AnimationHelper.makeAnimationChangeHeight(binding.moreInfoView, 0, 500, 400)
        AnimationHelper.makeAnimationUpDown(binding.viewpager, 800, 400)
        AnimationHelper.makeAnimationUpDown(binding.videoView, 800, 400)
    }

    private fun hideMoreInfo() {
        AnimationHelper.makeAnimationChangeHeight(binding.moreInfoView, 500, 0, 400)
        AnimationHelper.makeAnimationUpDown(binding.viewpager, -800, 400)
        AnimationHelper.makeAnimationUpDown(binding.videoView, -800, 400)
    }

}