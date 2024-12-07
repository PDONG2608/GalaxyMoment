package com.example.galaxymoment.utils

import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.adapter.TimeLineAdapter

class LogicUtils {
    companion object {
        fun setSpanSize(
            mTimelineAdapter: TimeLineAdapter,
            layoutManager: GridLayoutManager,
            numberSpan: Int
        ) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mTimelineAdapter.getItemViewType(position) == TimeLineAdapter.TYPE_HEADER) numberSpan else 1
                }
            }
        }
    }
}