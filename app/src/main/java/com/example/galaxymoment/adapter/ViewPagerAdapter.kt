package com.example.galaxymoment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.databinding.ItemViewPagerBinding
import com.example.galaxymoment.viewholder.ViewPagerViewHolder
import com.example.galaxymoment.viewmodel.DetailViewModel

class ViewPagerAdapter(private var viewModel: DetailViewModel) :
    RecyclerView.Adapter<ViewPagerViewHolder>() {
    private var binding: ItemViewPagerBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        binding = ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding!!, viewModel)
    }

    override fun getItemCount(): Int {
        return viewModel.getListItemDetail().size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bindView(viewModel.getListItemDetail()[position])
    }
}