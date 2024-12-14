package com.example.galaxymoment.viewholder

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.media.MediaPlayer.SEEK_CLOSEST
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemViewPagerBinding
import com.example.galaxymoment.manager.MoreInfoManager
import com.example.galaxymoment.utils.AnimationHelper
import com.example.galaxymoment.utils.Constants
import com.example.galaxymoment.utils.OnSwipeTouchListener
import com.example.galaxymoment.viewmodel.DetailViewModel

class ViewPagerViewHolder(
    private var binding: ItemViewPagerBinding,
    private val viewModel: DetailViewModel
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var moreInfoManger: MoreInfoManager

    fun bindView(item: MediaItems) {
        Glide.with(binding.singlePageImage.context)
            .load(item.uri)
            .frame(0)
            .fitCenter()
            .into(binding.singlePageImage)
        moreInfoManger = MoreInfoManager(item, binding, viewModel)
        swipeUpDownVideo()
        viewModel.isShowMoreInfo.observe(binding.root.context as LifecycleOwner) {
            if (it) showMoreInfo() else hideMoreInfo()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun swipeUpDownVideo() {
        binding.itemVideoView.setOnTouchListener(object :
            OnSwipeTouchListener(binding.itemVideoView.context) {
            override fun onSwipeTop() {
                if (viewModel.isShowMoreInfo.value == false || viewModel.isShowMoreInfo.value == null) {
                    viewModel.setShowMoreInfo(true)
                }
            }

            override fun onSwipeBottom() {
                if (viewModel.isShowMoreInfo.value == true) {
                    viewModel.setShowMoreInfo(false)
                }
            }
        })
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
        if(binding.itemVideoView.isPlaying){
            binding.itemVideoView.stopPlayback()
            binding.itemVideoView.alpha = 0f
        }
    }

    private fun showMoreInfo() {
        AnimationHelper.makeAnimationChangeHeight(binding.moreInfoView, 0, 400, Constants.DURATION_ANIM_MORE_INFO)
    }

    private fun hideMoreInfo() {
        AnimationHelper.makeAnimationChangeHeight(binding.moreInfoView, 400, 0, Constants.DURATION_ANIM_MORE_INFO)
    }
}

