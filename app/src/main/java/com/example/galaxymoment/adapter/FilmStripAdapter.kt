package com.example.galaxymoment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.databinding.ItemFilmStripBinding
import com.example.galaxymoment.databinding.ItemFilmStripDummyBinding
import com.example.galaxymoment.viewholder.FilmStripDummyViewHolder
import com.example.galaxymoment.viewholder.FilmStripViewHolder
import com.example.galaxymoment.viewmodel.DetailViewModel

class FilmStripAdapter(private val mDetailViewModel: DetailViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class FilmStripType(type: Int) {
        TYPE_DUMMY(0), TYPE_ITEM(1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || position == itemCount - 1) FilmStripType.TYPE_DUMMY.ordinal else FilmStripType.TYPE_ITEM.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == FilmStripType.TYPE_DUMMY.ordinal){
            val binding = ItemFilmStripDummyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FilmStripDummyViewHolder(binding)
        }
        val binding = ItemFilmStripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmStripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is FilmStripDummyViewHolder -> holder.bindDummy()
            is FilmStripViewHolder -> holder.bindThumb(mDetailViewModel.listItemDetail.value!![position-1])
        }
    }

    override fun getItemCount(): Int {
        return mDetailViewModel.getListItemDetail().size + 2
    }
}