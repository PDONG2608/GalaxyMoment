package com.example.galaxymoment.callback

import android.content.Context
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.data.TimeLineType
import java.util.TreeMap

interface IRepository {
    fun getListItemDetail(context: Context) : ArrayList<MediaItems>
    fun getListItemTimeLine(context: Context) : ArrayList<TimeLineType>
    fun getTreeMapTag(): TreeMap<String, ArrayList<MediaItems>>
}