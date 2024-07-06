package com.example.galaxymoment.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.adapter.VideoAdapter
import com.example.galaxymoment.databinding.FragmentTimelineBinding
import com.example.galaxymoment.viewmodel.VideoViewModel

class TimelineFragment : Fragment() {
    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[VideoViewModel::class.java]

        val adapter = VideoAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.recyclerView.adapter = adapter

        viewModel.videoList.observe(viewLifecycleOwner, Observer { videoList ->
            adapter.submitList(videoList.toMutableList())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
