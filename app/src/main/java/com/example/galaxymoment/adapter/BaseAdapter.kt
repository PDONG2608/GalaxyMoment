package com.example.galaxymoment.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.callback.ITouchListener
import com.example.galaxymoment.data.TimeLineType

abstract class BaseAdapter<T>(
    protected val items: List<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected lateinit var mITouchListener: ITouchListener

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TimeLineType.TypeHeader -> TYPE_HEADER
            else -> TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int = items.size

    abstract fun createHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun createContentViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> createHeaderViewHolder(parent)
            TYPE_CONTENT -> createContentViewHolder(parent)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    fun setITouchListener(iTouchListener: ITouchListener) {
        mITouchListener = iTouchListener
    }
}
