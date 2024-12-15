package com.example.galaxymoment.viewholder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.databinding.ItemContentTimeLineBinding

class TimelineContentViewHolder(private val binding: ItemContentTimeLineBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindThumb(videoItem: TimeLineType.TypeContent) {
        Glide.with(binding.videoThumbnail.context)
            .load(videoItem.mediaItems.uri)
            .frame(0)
            .centerCrop()
            .into(binding.videoThumbnail)
        binding.videoThumbnail.transitionName = videoItem.mediaItems.uri.toString()
    }

    fun getView(): ImageView {
        return binding.videoThumbnail
    }
}