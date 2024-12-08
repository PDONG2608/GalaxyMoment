package com.example.galaxymoment.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.galaxymoment.callback.IRepository
import com.example.galaxymoment.data.HeaderItem
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.data.TimeLineType
import java.text.SimpleDateFormat

class RepositoryImpl : IRepository {
    override fun getListItemDetail(context: Context): ArrayList<MediaItems> {
        return getVideo(context)
    }

    override fun getListItemTimeLine(context: Context): ArrayList<TimeLineType> {
        return formatToTypeTimeLine(getVideo(context))
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatToTypeTimeLine(listVideo: ArrayList<MediaItems>): ArrayList<TimeLineType> {
        val finalList = ArrayList<TimeLineType>()
        val mapGroupedItems = listVideo.groupBy { SimpleDateFormat("dd-MM-yyyy").format(it.date) }
            .mapValues { ArrayList(it.value) }
        mapGroupedItems.keys.forEach { key ->
            finalList.add(TimeLineType.TypeHeader(HeaderItem(key, false)))
            finalList.addAll(mapGroupedItems[key]!!.map { TimeLineType.TypeContent(it) })
        }
        return finalList
    }


    private fun getVideo(context: Context): ArrayList<MediaItems> {
        val projection = arrayOf(
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.RESOLUTION,
        )
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Video.Media.DATE_TAKEN + " DESC"
        )
        val listItem = ArrayList<MediaItems>()
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val dateTakenColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)
            val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val resolutionColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(id.toString()).build()
                val duration = cursor.getLong(durationColumn)
                val dateTaken = cursor.getLong(dateTakenColumn)
                val dateAdded = cursor.getLong(dateAddedColumn)
                var dateModified = cursor.getLong(dateModifiedColumn)
                val path = cursor.getString(pathColumn)
                val size = cursor.getLong(sizeColumn)
                val resolution = cursor.getString(resolutionColumn)
                val videoCodec = "abc"
                val videoFps = 111
                val resDate = if(dateTaken != 0L) dateTaken else dateAdded
                listItem.add(
                    MediaItems(
                        uri,
                        duration,
                        resDate,
                        path,
                        size,
                        resolution,
                        videoCodec,
                        videoFps,
                        false
                    )
                )
            }
        }
        return listItem
    }
}