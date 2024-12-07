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
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATE_TAKEN
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
            val dateTaken = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val duration = cursor.getLong(durationColumn)
                val date = cursor.getLong(dateTaken)
                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(id.toString()).build().toString()
                listItem.add(MediaItems(uri, duration, date, false))
            }
        }
        return listItem
    }
}