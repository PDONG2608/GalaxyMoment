package com.example.galaxymoment.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.fragment.SinglePageFragment
import com.example.galaxymoment.viewmodel.VideoViewModel

class PagerAdapter(
    private var fragmentActivity: FragmentActivity,
    private val mediaItems: List<MediaItems>
) : FragmentStateAdapter(fragmentActivity) {
    private val mapFragment = HashMap<String,SinglePageFragment>()
    private lateinit var viewModel: VideoViewModel
    override fun getItemCount(): Int {
        return mediaItems.size
    }

    override fun createFragment(position: Int): Fragment {
        viewModel = ViewModelProvider(fragmentActivity)[VideoViewModel::class.java]
        return if (mapFragment.containsKey(mediaItems[position].path)) {
            Log.i("dongdong","mapFragment.containsKey")
            mapFragment[mediaItems[position].path]!!
        } else {
            Log.i("dongdong","mapFragment.not containsKey")
            val fragment = SinglePageFragment(mediaItems[position].path)
            mapFragment[mediaItems[position].path] = fragment
            fragment
        }
    }
}