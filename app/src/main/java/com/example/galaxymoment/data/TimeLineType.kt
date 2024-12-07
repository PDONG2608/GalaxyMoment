package com.example.galaxymoment.data

sealed class TimeLineType {
    data class TypeHeader(val headerItem: HeaderItem) : TimeLineType()
    data class TypeContent(val mediaItems: MediaItems) : TimeLineType()
}

