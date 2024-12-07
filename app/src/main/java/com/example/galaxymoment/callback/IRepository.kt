package com.example.galaxymoment.callback

import android.content.Context
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.data.TimeLineType

interface IRepository {
    fun getListItemDetail(context: Context) : ArrayList<MediaItems>
    fun getListItemTimeLine(context: Context) : ArrayList<TimeLineType>
}