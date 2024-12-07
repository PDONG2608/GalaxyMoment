package com.example.galaxymoment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemFilmStripBinding

class FilmStripViewHolder (private val binding: ItemFilmStripBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindThumb(mediaItems: MediaItems) {
        Glide.with(binding.root.context)
            .load(mediaItems.path)
            .frame(0)
            .centerCrop()
            .into(binding.itemFilmStripImage)
    }

}