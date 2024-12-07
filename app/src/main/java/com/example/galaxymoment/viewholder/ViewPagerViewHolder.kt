package com.example.galaxymoment.viewholder

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemViewPagerBinding

class ViewPagerViewHolder(private var binding: ItemViewPagerBinding) : RecyclerView.ViewHolder(binding.root){
    fun bindView(item: MediaItems){
        Glide.with(binding.singlePageImage.context)
            .load(Uri.parse(item.path))
            .frame(0)
            .fitCenter()
            .into(binding.singlePageImage)
    }
}