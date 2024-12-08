package com.example.galaxymoment.viewholder

import android.net.Uri
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

    fun startVideo(item: MediaItems){
        binding.itemVideoView.visibility = RecyclerView.VISIBLE
        binding.itemVideoView.setVideoURI(Uri.parse(item.uri.toString()))
        binding.itemVideoView.start()
    }

    fun stopVideo(){
        binding.itemVideoView.stopPlayback()
        binding.itemVideoView.visibility = RecyclerView.GONE
        binding.itemVideoView.suspend()
    }
}