package com.example.galaxymoment.data

import android.net.Uri

data class MediaItems(
    val uri: Uri,
    val duration: Long,
    val date: Long,
    val path: String,
    val size: Long,
    val resolution: String,
    val videoCodec: String,
    val fps : Int,
    val isChecked: Boolean
) {
}
