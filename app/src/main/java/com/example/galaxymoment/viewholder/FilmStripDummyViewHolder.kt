package com.example.galaxymoment.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxymoment.R
import com.example.galaxymoment.databinding.ItemFilmStripDummyBinding

class FilmStripDummyViewHolder (private val binding: ItemFilmStripDummyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindDummy() {
        val layoutParams = binding.root.layoutParams
        layoutParams.width = getHaftScreenWidth(binding.root.context)
        binding.root.layoutParams = layoutParams
    }

    private fun getHaftScreenWidth(context: Context?): Int {
        return if(context != null){
            val displayMetrics = context.resources.displayMetrics
            displayMetrics.widthPixels.div(2)  - context.resources.getDimensionPixelSize(R.dimen.width_item_film_strip)/2
        } else {
            0
        }
    }

}