package com.example.galaxymoment.utils

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaExtractor
import android.media.MediaFormat
import android.net.Uri
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.galaxymoment.adapter.BaseAdapter
import com.example.galaxymoment.adapter.TimeLineAdapter
import com.example.galaxymoment.data.HeaderItem
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.viewmodel.DetailViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogicUtils {
    companion object {

        /**
         * @param mTimelineAdapter: adapter of recyclerView
         * @param layoutManager: layout of recyclerView
         * @param numberSpan: number of span
         * set span size of item in recyclerView
         */
        fun setSpanSize(
            mTimelineAdapter: Adapter<*>?,
            layoutManager: GridLayoutManager,
            numberSpan: Int
        ) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mTimelineAdapter?.getItemViewType(position) == BaseAdapter.TYPE_HEADER) numberSpan else 1
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
            return findSameItemPosition(viewModel.getListItemDetail(), mTimelineUri)
        }

        private fun findSameItemPosition(
            listItemDetail: ArrayList<MediaItems>,
            mTimelineUri: String
        ): Int {
            for (i in listItemDetail.indices) {
                if (listItemDetail[i].uri.toString() == mTimelineUri) return i
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
                if (i != array.size - 2) {
                    res += "/"
                }
            }
            return res
        }

        /**
         * Converts a given time in milliseconds to a formatted date string.
         *
         * @param timeInMillis The time in milliseconds.
         * @return A string representing the date in the format "EEEE, dd/MM/yyyy".
         */
        fun convertLongToDate(timeInMillis: Long): String {
            val dateFormat = SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.ENGLISH)
            val date = Date(timeInMillis)
            return dateFormat.format(date)
        }


        /**
         * get video codec, fps from uri
         * @param uri: uri of video
         * @param context: context
         * @return video codec
         */
        @SuppressLint("Recycle")
        fun getCodecAndFps(uri: Uri, context: Context): String {
            val extractor = MediaExtractor()
            val fd = context.contentResolver.openFileDescriptor(uri, "r")?.fileDescriptor
            if (fd != null) {
                extractor.setDataSource(fd)
            }
            for (i in 0 until extractor.trackCount) {
                val format = extractor.getTrackFormat(i)
                if (format.getString(MediaFormat.KEY_MIME)?.startsWith("video/") == true) {
                    val fps = format.getInteger(MediaFormat.KEY_FRAME_RATE)
                    when (format.getString(MediaFormat.KEY_MIME)) {
                        "video/avc" -> return "H.264/$fps"
                        "video/hevc" -> return "H.265/$fps"
                        "video/mpeg" -> return "MPEG/$fps"
                        "video/mp4" -> return "MPEG-4/$fps"
                    }
                }
            }
            extractor.release()
            return "Unknown Codec"
        }

        fun formatToTypeTimeLine(listVideo: ArrayList<MediaItems>): ArrayList<TimeLineType> {
            val finalList = ArrayList<TimeLineType>()
            val mapGroupedItems = listVideo.groupBy { SimpleDateFormat("dd-MM-yyyy").format(it.date) }
                .mapValues { ArrayList(it.value) }
            mapGroupedItems.keys.forEach { key ->
                finalList.add(TimeLineType.TypeHeader(HeaderItem(key, false)))
                finalList.addAll(mapGroupedItems[key]!!.map { TimeLineType.TypeContent(it) })
            }
            return finalList
        }
    }
}