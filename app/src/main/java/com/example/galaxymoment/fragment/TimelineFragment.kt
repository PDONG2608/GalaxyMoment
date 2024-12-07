package com.example.galaxymoment.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.R
import com.example.galaxymoment.adapter.TimeLineAdapter
import com.example.galaxymoment.callback.ITouchListener
import com.example.galaxymoment.databinding.FragmentTimelineBinding
import com.example.galaxymoment.viewmodel.TimelineViewModel
import kotlin.math.abs

class TimelineFragment : Fragment(), ITouchListener {
    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TimelineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TimelineViewModel::class.java]
        val adapter = TimeLineAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.recyclerView.adapter = adapter

        viewModel.videoList.observe(viewLifecycleOwner, Observer { videoList ->
            adapter.submitList(videoList.toMutableList())
        })
        adapter.setOnClickTimeLineListener(this)
        binding.appBarTimeLine.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()
            binding.textCollapsingToolbar.alpha = (1 - percentage * 2).toFloat()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickTimeline(uri: String, position: Int) {
        viewModel.setCurrentPosPager(position)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.timeLineLayout, DetailFragment())
            .addToBackStack(null)
            .commit();
    }
}
