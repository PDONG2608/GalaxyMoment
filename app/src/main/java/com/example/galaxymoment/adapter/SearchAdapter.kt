package com.example.galaxymoment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.databinding.ItemContentTimeLineBinding
import com.example.galaxymoment.databinding.ItemHeaderTimeLineBinding
import com.example.galaxymoment.viewholder.TimelineContentViewHolder
import com.example.galaxymoment.viewholder.TimelineHeaderViewHolder

class SearchAdapter(private val mTimeLineItem: ArrayList<TimeLineType>) : BaseAdapter<TimeLineType>(mTimeLineItem) {
    override fun createHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemHeaderTimeLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimelineHeaderViewHolder(binding)
    }

    override fun createContentViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemContentTimeLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimelineContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TimelineHeaderViewHolder -> {
                holder.bindHeader(mTimeLineItem[position] as TimeLineType.TypeHeader)
            }
            is TimelineContentViewHolder -> {
                holder.bindThumb(mTimeLineItem[position] as TimeLineType.TypeContent)
            }
        }
    }
}