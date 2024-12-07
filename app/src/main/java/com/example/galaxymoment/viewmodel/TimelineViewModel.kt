package com.example.galaxymoment.viewmodel

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galaxymoment.data.MediaItems


class TimelineViewModel : ViewModel() {
    private val _videoList = MutableLiveData<MutableList<MediaItems>>()
    val videoList: LiveData<MutableList<MediaItems>> = _videoList

    private val _currentPosPager = MutableLiveData<Int>()
    val currentPosPager: LiveData<Int> = _currentPosPager

    fun setCurrentPosPager(position: Int) {
        _currentPosPager.value = position
    }

    fun loadVideos(context: Context) {
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

        cursor?.use {
            val videos = mutableListOf<MediaItems>()
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val duration = cursor.getLong(durationColumn)
                val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(id.toString()).build().toString()
                videos.add(MediaItems(uri, duration))
            }
            _videoList.value = videos
        }
    }
}