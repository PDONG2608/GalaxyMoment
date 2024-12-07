package com.example.galaxymoment.repository

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.galaxymoment.callback.IRepository
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.data.TimeLineType

class RepositoryImpl : IRepository {
    override fun getListItemDetail(context: Context) : ArrayList<MediaItems> {
        return getVideo(context)
    }

    override fun getListItemTimeLine(context: Context): ArrayList<TimeLineType> {
        TODO("Not yet implemented")
    }

    private fun getVideo(context: Context): ArrayList<MediaItems> {
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DURATION
        )
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        val listItem = ArrayList<MediaItems>()
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val duration = cursor.getLong(durationColumn)
                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(id.toString()).build().toString()
                listItem.add(MediaItems(uri, duration, false))
            }
        }
        return listItem
    }
}