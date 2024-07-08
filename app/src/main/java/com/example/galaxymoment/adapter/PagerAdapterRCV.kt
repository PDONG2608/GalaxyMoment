package com.example.galaxymoment.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.FragmentSinglePageBinding

class PagerAdapterRCV(private var mediaItems: List<MediaItems>) : RecyclerView.Adapter<PagerAdapterRCV.ViewHolder>(){
    private var binding: FragmentSinglePageBinding? = null
    inner class ViewHolder(private var binding: FragmentSinglePageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindView(item: MediaItems){
            Glide.with(binding.singlePageImage.context)
                .load(Uri.parse(item.path))
                .frame(0)
                .fitCenter()
                .into(binding.singlePageImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FragmentSinglePageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun getItemCount(): Int {
        return mediaItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mediaItems[position])
    }
}