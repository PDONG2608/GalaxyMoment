package com.example.galaxymoment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemViewPagerBinding
import com.example.galaxymoment.viewholder.ViewPagerViewHolder

class ViewPagerAdapter(private var mMediaItems: List<MediaItems>) :
    RecyclerView.Adapter<ViewPagerViewHolder>() {
    private var binding: ItemViewPagerBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        binding = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return mMediaItems.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bindView(mMediaItems[position])
    }
}