package com.example.galaxymoment.utils

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class SlowFlingRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    private val flingFactor = 0.4f  // Giảm tốc độ fling xuống 50%

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        return super.fling((velocityX * flingFactor).toInt(), (velocityY * flingFactor).toInt())
    }
}