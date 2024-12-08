package com.example.galaxymoment.viewholder

import android.animation.ObjectAnimator
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer.SEEK_CLOSEST
import android.media.MediaPlayer.SEEK_CLOSEST_SYNC
import android.media.MediaPlayer.SEEK_NEXT_SYNC
import android.media.MediaPlayer.SEEK_PREVIOUS_SYNC
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemViewPagerBinding

class ViewPagerViewHolder(private var binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root){
    fun bindView(item: MediaItems){
        Log.i("dongdong", "ViewPagerViewHolder bindView")
        Glide.with(binding.singlePageImage.context)
            .load(item.uri)
            .frame(0)
            .fitCenter()
            .into(binding.singlePageImage)
    }

    fun startVideo(item: MediaItems) {
        binding.itemVideoView.setVideoURI(Uri.parse(item.uri.toString()))
        binding.itemVideoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.start()
            mediaPlayer.setVolume(0f, 0f)
            Handler(Looper.getMainLooper()).postDelayed({
                mediaPlayer.setVolume(1f, 1f)
                mediaPlayer.seekTo(0, SEEK_CLOSEST)
                ObjectAnimator.ofFloat(binding.itemVideoView, "alpha", 0f, 1f).apply {
                    duration = 500
                    start()
                }
            }, 500)
        }

    }

    fun stopVideo(){
        binding.itemVideoView.stopPlayback()
        binding.itemVideoView.alpha = 0f
    }
}