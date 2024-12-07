package com.example.galaxymoment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.databinding.ItemHeaderTimeLineBinding

class TimelineHeaderViewHolder(private val binding: ItemHeaderTimeLineBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindHeader(videoItem: TimeLineType.TypeHeader) {
        binding.textTitle.text = videoItem.headerItem.date
    }
}