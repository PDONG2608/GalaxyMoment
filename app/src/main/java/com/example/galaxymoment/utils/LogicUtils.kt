package com.example.galaxymoment.utils

import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.adapter.TimeLineAdapter
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.viewmodel.DetailViewModel

class LogicUtils {
    companion object {

        /**
         * @param mTimelineAdapter: adapter of recyclerView
         * @param layoutManager: layout of recyclerView
         * @param numberSpan: number of span
         * set span size of item in recyclerView
         */
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


        /**
         * calculate position of item in recyclerView when user open detail
         * @param viewModel: viewModel of detail fragment
         * @param mTimelineUri: path of video
         * @return position of item in recyclerView
         */
        fun calculatePositionOpenDetail(viewModel: DetailViewModel, mTimelineUri: String): Int {
            return findSameItemPosition( viewModel.getListItemDetail(), mTimelineUri)
        }

        private fun findSameItemPosition(
            listItemDetail: ArrayList<MediaItems>,
            mTimelineUri: String
        ) : Int {
            for ( i in listItemDetail.indices) {
                if (listItemDetail[i].uri == mTimelineUri) return i
            }
            return -1
        }


        /**
         * @param path: path of video
         * @return path of video was format
         * example: /storage/emulated/0/DCIM/Camera/VID_20220101_132333.mp4
         * to: VID_20220101_132333.mp4
         *     storage/emulated/0/DCIM/Camera
         */
        fun formatMoreInfoPath(path: String): String {
            var res = ""
            val array = path.split("/")
            array[array.size - 1]
            res += array[array.size - 1]
            res += "\n"
            for (i in 1 until array.size - 1) {
                res += array[i]
                if(i != array.size - 2){
                    res += "/"
                }
            }
            return res
        }
    }
}