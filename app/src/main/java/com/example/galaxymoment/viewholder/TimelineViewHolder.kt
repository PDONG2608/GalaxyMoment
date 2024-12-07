package com.example.galaxymoment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemVideoBinding

class TimelineViewHolder(private val binding: ItemVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindThumb(videoItem: MediaItems, position: Int) {
        Glide.with(binding.videoThumbnail.context)
            .load(videoItem.path)
            .frame(0)
            .centerCrop()
            .into(binding.videoThumbnail)
    }
}