package com.example.galaxymoment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.callback.ITouchListener
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.databinding.ItemContentTimeLineBinding
import com.example.galaxymoment.databinding.ItemHeaderTimeLineBinding
import com.example.galaxymoment.viewholder.TimelineContentViewHolder
import com.example.galaxymoment.viewholder.TimelineHeaderViewHolder
import com.example.galaxymoment.viewmodel.TimelineViewModel

class TimeLineAdapter(private val mTimelineViewModel: TimelineViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var mITouchListener: ITouchListener
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        if (mTimelineViewModel.getListItemTimeLine()[position] is TimeLineType.TypeHeader) return TYPE_HEADER
        return TYPE_CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_HEADER -> {
                val binding = ItemHeaderTimeLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TimelineHeaderViewHolder(binding)
            }

            else -> {
                val binding = ItemContentTimeLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TimelineContentViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       when(holder){
           is TimelineHeaderViewHolder -> holder.bindHeader(mTimelineViewModel.getListItemTimeLine()[position] as TimeLineType.TypeHeader)
           is TimelineContentViewHolder -> holder.bindThumb(mTimelineViewModel.getListItemTimeLine()[position] as TimeLineType.TypeContent)
       }
        holder.itemView.setOnClickListener {
            if(mTimelineViewModel.getListItemTimeLine()[position] is TimeLineType.TypeContent) {
                mITouchListener.onClickTimeline((mTimelineViewModel.getListItemTimeLine()[position] as TimeLineType.TypeContent).mediaItems.path, position)
            }
        }
    }

    override fun getItemCount(): Int = mTimelineViewModel.getListItemTimeLine().size

    fun setITouchListener(iTouchListener: ITouchListener) {
        mITouchListener = iTouchListener
    }
}
