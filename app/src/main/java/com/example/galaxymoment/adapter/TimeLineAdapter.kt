package com.example.galaxymoment.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.galaxymoment.callback.ITouchListener
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemVideoBinding
import com.example.galaxymoment.viewholder.TimelineViewHolder

class TimeLineAdapter : ListAdapter<MediaItems, TimelineViewHolder>(DiffCallback()) {

    private var mOnClickTimeLineListener : ITouchListener ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimelineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bindThumb(getItem(position), position)
        holder.itemView.setOnClickListener {
            Log.i("dongdong","setOnClickTimeLineListener")
            mOnClickTimeLineListener?.onClickTimeline(getItem(position).path,position)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MediaItems>() {
        override fun areItemsTheSame(oldItem: MediaItems, newItem: MediaItems): Boolean {
            return oldItem.path == newItem.path
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: MediaItems, newItem: MediaItems): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnClickTimeLineListener(listener: ITouchListener){
        mOnClickTimeLineListener = listener
    }
}
