package com.example.galaxymoment.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.galaxymoment.data.TimeLineType

class TimeLineDiffCallback(
    private val oldList: ArrayList<TimeLineType>,
    private val newList: ArrayList<TimeLineType>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is TimeLineType.TypeHeader && newItem is TimeLineType.TypeHeader) {
            return oldItem.headerItem.date == newItem.headerItem.date
        } else if (oldItem is TimeLineType.TypeContent && newItem is TimeLineType.TypeContent) {
            return oldItem.mediaItems.uri == newItem.mediaItems.uri
        }
        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}