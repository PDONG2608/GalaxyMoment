package com.example.galaxymoment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.fragment.MediaFragment

class PagerAdapter(
    fragmentActivity: FragmentActivity,
    private val mediaItems: List<MediaItems>
) : FragmentStateAdapter(fragmentActivity) {
    private val listFragment = ArrayList<MediaFragment>()
    override fun getItemCount(): Int {
        return mediaItems.size
    }

    override fun createFragment(position: Int): Fragment {
        val mediaItem = mediaItems[position]
        val fragment = MediaFragment.newInstance(mediaItem.path)
        listFragment.add(fragment)
        return fragment
    }
}