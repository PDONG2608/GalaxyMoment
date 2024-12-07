package com.example.galaxymoment.utils

import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.adapter.TimeLineAdapter
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.viewmodel.DetailViewModel

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

        fun calculatePositionOpenDetail(viewModel: DetailViewModel, mTimelineUri: String): Int {
            return findSameItemPosition( viewModel.getListItemDetail(), mTimelineUri)
        }

        private fun findSameItemPosition(
            listItemDetail: ArrayList<MediaItems>,
            mTimelineUri: String
        ) : Int {
            for ( i in listItemDetail.indices) {
                if (listItemDetail[i].path == mTimelineUri) return i
            }
            return -1
        }
    }
}