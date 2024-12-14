package com.example.galaxymoment.manager

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.galaxymoment.adapter.ViewPagerAdapter
import com.example.galaxymoment.databinding.FragmentDetailBinding
import com.example.galaxymoment.utils.AnimationHelper
import com.example.galaxymoment.utils.OffsetHelper
import com.example.galaxymoment.utils.Constants
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.viewholder.ViewPagerViewHolder
import com.example.galaxymoment.viewmodel.DetailViewModel

class ViewPagerManager(
    private val mDetailViewModel: DetailViewModel,
    private val binding: FragmentDetailBinding,
    private val arguments: Bundle?
) {
    private lateinit var mTimelineUri: String
    private lateinit var mViewPagerAdapter: ViewPagerAdapter
    private var mViewPager: ViewPager2 = binding.viewpager
    private var oldPosition = -1
    private lateinit var mNewViewHolder: ViewPagerViewHolder
    private var mListViewHolder = ArrayList<ViewPagerViewHolder>()

    init {
        arguments?.let {
            mTimelineUri = it.getString("timeLineUri").toString()
        }
        val positionOpenDetailView = LogicUtils.calculatePositionOpenDetail(mDetailViewModel,mTimelineUri)
        Log.i("dongdong", "ViewPagerManager positionOpenDetailView = $positionOpenDetailView")
        mViewPagerAdapter = ViewPagerAdapter(mDetailViewModel)
        mViewPager.adapter = mViewPagerAdapter
        mViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mViewPager.setCurrentItem(positionOpenDetailView, false)
        mViewPager.offscreenPageLimit = 1
        val recyclerView = mViewPager.getChildAt(0) as RecyclerView
        recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool())
        viewPagerListener()
        buttonMoreInfoClick()
        moreInfoListener()
        handleFilmStripScrolling()
        observe()
    }

    private fun handleFilmStripScrolling() {
        mDetailViewModel.currentPosFilmStrip.observe(mDetailViewModel.getContext() as LifecycleOwner) {
//            mViewPager.setCurrentItem(it - 1, false)
//            for (viewHolder in mListViewHolder) {
//                viewHolder.stopVideo()
//            }
        }
        mDetailViewModel.isFilmStripScrolling.observe(mDetailViewModel.getContext() as LifecycleOwner) {
            if (it == true) {
            } else {
                for (viewHolder in mListViewHolder){
                    viewHolder.stopVideo()
                }
//                if(mNewViewHolder != null){
//                    mNewViewHolder.startVideo(mDetailViewModel.getListItemDetail()[mDetailViewModel.currentPosPager.value!!])
//                }
            }
        }
    }

    private fun viewPagerListener() {
        mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.i("dongdong", "ViewPager2Fragment onPageSelected " + position)
                super.onPageSelected(position)
                mDetailViewModel.setCurrentPosPager(position)
                val recyclerView = mViewPager.getChildAt(0) as RecyclerView
                if (recyclerView.findViewHolderForAdapterPosition(position) != null) {
                    mNewViewHolder = recyclerView.findViewHolderForAdapterPosition(position) as ViewPagerViewHolder
                    if (!mListViewHolder.contains(mNewViewHolder)) {
                        mListViewHolder.add(mNewViewHolder)
                    }
                }
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
//                OffsetHelper.setOffSetForVideo(binding, mDetailViewModel.currentPosPager.value!!, position, positionOffsetPixels)
                if (OffsetHelper.checkTypeSwipe == Constants.DEFAULT) {
//                    Log.i("dongdong", "setURI checkTypeSwipe : SWIPE_TO_RIGHT")
                    oldPosition = position
                }
            }
        })
    }

    private fun moreInfoListener() {
        mDetailViewModel.isShowMoreInfo.observe(mDetailViewModel.getContext() as LifecycleOwner) {
            if (it == true) {
                AnimationHelper.makeAnimationFadeOut(binding.filmStrip, Constants.DURATION_ANIM_MORE_INFO)
                AnimationHelper.makeAnimationFadeOut(binding.buttonOpenMoreinfo, Constants.DURATION_ANIM_MORE_INFO)
            } else {
                AnimationHelper.makeAnimationFadeIn(binding.filmStrip, Constants.DURATION_ANIM_MORE_INFO)
                AnimationHelper.makeAnimationFadeIn(binding.buttonOpenMoreinfo, Constants.DURATION_ANIM_MORE_INFO)
            }
        }
    }
    private fun buttonMoreInfoClick() {
        binding.buttonOpenMoreinfo.setOnClickListener {
            if (mDetailViewModel.isShowMoreInfo.value == null || mDetailViewModel.isShowMoreInfo.value == false) {
                mDetailViewModel.setShowMoreInfo(true)
            }
        }
    }

    private fun observe() {
        mDetailViewModel.currentPosPager.observe(mDetailViewModel.getContext() as LifecycleOwner) {position ->
//           mViewPager.setCurrentItem(position, false)
        }
    }
}
