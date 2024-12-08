package com.example.galaxymoment.viewholder

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.media.MediaPlayer.SEEK_CLOSEST
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemViewPagerBinding
import com.example.galaxymoment.manager.MoreInfoManager
import com.example.galaxymoment.utils.AnimationHelper
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.utils.OnSwipeTouchListener

class ViewPagerViewHolder(private var binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root){
    private lateinit var moreInfoManger: MoreInfoManager

    fun bindView(item: MediaItems){
        Glide.with(binding.singlePageImage.context)
            .load(item.uri)
            .frame(0)
            .fitCenter()
            .into(binding.singlePageImage)
        moreInfoManger = MoreInfoManager(item, binding)
        swipeUpDownVideo()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun swipeUpDownVideo() {
        binding.itemVideoView.setOnTouchListener(object : OnSwipeTouchListener( binding.itemVideoView.context) {
            override fun onSwipeTop() {
                showMoreInfo()
            }

            override fun onSwipeBottom() {
                hideMoreInfo()
            }
        })
    }

    private fun showMoreInfo() {
        LogicUtils.isShowMoreInfo = true
        AnimationHelper.makeAnimationChangeHeight(binding.moreInfoView, 0, 500, 400)
        AnimationHelper.makeAnimationUpDown(binding.itemVideoView, 800, 400)
        AnimationHelper.makeAnimationUpDown(binding.singlePageImage, 800, 400)
    }

    private fun hideMoreInfo() {
        LogicUtils.isShowMoreInfo = false
        AnimationHelper.makeAnimationChangeHeight(binding.moreInfoView, 500, 0, 400)
        AnimationHelper.makeAnimationUpDown(binding.itemVideoView, -800, 400)
        AnimationHelper.makeAnimationUpDown(binding.singlePageImage, -800, 400)
    }



    fun startVideo(item: MediaItems) {
        binding.itemVideoView.setVideoURI(Uri.parse(item.uri.toString()))
        binding.itemVideoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setVolume(0f, 0f)
            mediaPlayer.start()
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    mediaPlayer.setVolume(1f, 1f)
                    mediaPlayer.seekTo(0, SEEK_CLOSEST)
                    ObjectAnimator.ofFloat(binding.itemVideoView, "alpha", 0f, 1f).apply {
                        duration = 500
                        start()
                    }
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }, 500)
        }

    }

    fun stopVideo() {
        binding.itemVideoView.stopPlayback()
        binding.itemVideoView.alpha = 0f
    }
}