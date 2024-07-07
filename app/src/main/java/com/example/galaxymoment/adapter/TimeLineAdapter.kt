package com.example.galaxymoment.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemVideoBinding

class TimeLineAdapter : ListAdapter<MediaItems, TimeLineAdapter.VideoViewHolder>(DiffCallback()) {

    private var mOnClickTimeLineListener : OnClickTimeLineListener ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bindThumb(getItem(position), position)
        holder.itemView.setOnClickListener {
            Log.i("dongdong","setOnClickTimeLineListener")
            mOnClickTimeLineListener?.onClick(getItem(position).path,position)
        }
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindThumb(videoItem: MediaItems, position: Int) {
            Glide.with(binding.videoThumbnail.context)
                .load(videoItem.path)
                .frame(0)
                .centerCrop()
                .into(binding.videoThumbnail)
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

    fun setOnClickTimeLineListener(listener: OnClickTimeLineListener){
        mOnClickTimeLineListener = listener
        Log.i("dongdong","setOnClickTimeLineListener")
    }

    interface OnClickTimeLineListener {
        fun onClick(uri: String, position: Int)
    }
}
