package com.example.galaxymoment.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.galaxymoment.adapter.PagerAdapter
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.FragmentViewPager2Binding
import com.example.galaxymoment.viewmodel.VideoViewModel

class ViewPager2Fragment : Fragment() {
    private lateinit var viewModel: VideoViewModel
    private lateinit var viewPager : ViewPager2
    private var _binding: FragmentViewPager2Binding? = null
    private val binding get() = _binding!!
    private var oldPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPager2Binding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[VideoViewModel::class.java]
        viewPager = binding.fragmentViewPager2
        Log.i("dongdong","ViewPager2Fragment onCreateView listMediaItemSize = ${viewModel.videoList.value as List<MediaItems>}")
        val adapter = PagerAdapter(requireActivity(),viewModel.videoList.value as List<MediaItems>)
        viewPager.adapter = adapter
        viewModel.videoList.observe(viewLifecycleOwner) {
        }
        viewPager.setCurrentItem(viewModel.currentPosPager.value!!, false)
        viewPager.offscreenPageLimit = 1
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("dongdong","ViewPager2Fragment onViewCreated")

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.i("dongdong","ViewPager2Fragment onPageSelected position = $position")
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.i("dongdong","ViewPager2Fragment onPageScrollStateChanged state = $state")
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.i("dongdong","ViewPager2Fragment onPageScrolled position = $position positionOffset = $positionOffset positionOffsetPixels = $positionOffsetPixels")
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if(position != oldPosition){
                    oldPosition = position
                    binding.videoView.setVideoURI(Uri.parse(viewModel.videoList.value!![position].path))
                    binding.videoView.start()
                }
                binding.videoView.translationX = -positionOffsetPixels.toFloat()
            }
        })
    }
}