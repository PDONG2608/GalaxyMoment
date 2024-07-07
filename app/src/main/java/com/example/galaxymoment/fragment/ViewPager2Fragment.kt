package com.example.galaxymoment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.galaxymoment.adapter.PagerAdapter
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.FragmentViewPager2Binding
import com.example.galaxymoment.viewmodel.VideoViewModel

class ViewPager2Fragment : Fragment() {
    private lateinit var viewModel: VideoViewModel
    private var _binding: FragmentViewPager2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPager2Binding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[VideoViewModel::class.java]
         val viewPager = binding.fragmentViewPager2
        Log.i("dongdong","ViewPager2Fragment onCreateView listMediaItemSize = ${viewModel.videoList.value as List<MediaItems>}")
        val adapter = PagerAdapter(requireActivity(),viewModel.videoList.value as List<MediaItems>)
        viewPager.adapter = adapter
        viewModel.videoList.observe(viewLifecycleOwner) {
        }
        viewPager.setCurrentItem(viewModel.currentPosPager.value!!, false)
        viewPager.offscreenPageLimit = 1
        return binding.root
    }
}