package com.example.galaxymoment.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.galaxymoment.callback.ITouchListener
import com.example.galaxymoment.data.MediaItems

class DiffCallback : DiffUtil.ItemCallback<MediaItems>() {
    override fun areItemsTheSame(oldItem: MediaItems, newItem: MediaItems): Boolean {
        return oldItem.path == newItem.path
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MediaItems, newItem: MediaItems): Boolean {
        return oldItem == newItem
    }
}