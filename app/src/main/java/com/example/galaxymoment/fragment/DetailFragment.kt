package com.example.galaxymoment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.galaxymoment.callback.IVideoMainListener
import com.example.galaxymoment.databinding.FragmentViewPager2Binding
import com.example.galaxymoment.manager.ViewPagerManager
import com.example.galaxymoment.viewmodel.TimelineViewModel

class DetailFragment : Fragment() {
    private lateinit var mViewPagerManager: ViewPagerManager
    private lateinit var mTimelineViewModel: TimelineViewModel
    private var _binding: FragmentViewPager2Binding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPager2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.videoView.setOnCompletionListener {
            binding.videoView.start()
        }
    }

    private fun initView() {
        mTimelineViewModel = ViewModelProvider(requireActivity())[TimelineViewModel::class.java]
        mViewPagerManager = ViewPagerManager(mTimelineViewModel, binding)
    }
}