package com.example.galaxymoment.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.R
import com.example.galaxymoment.adapter.TimeLineAdapter
import com.example.galaxymoment.callback.ITouchListener
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.databinding.FragmentTimelineBinding
import com.example.galaxymoment.manager.TimeLineManager
import com.example.galaxymoment.viewmodel.TimelineViewModel
import kotlin.math.abs

class TimelineFragment : Fragment() {
    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!
    private lateinit var mTimelineViewModel: TimelineViewModel
    private lateinit var mTimelineManager: TimeLineManager
    private lateinit var mListItem: ArrayList<TimeLineType>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTimelineViewModel = ViewModelProvider(requireActivity())[TimelineViewModel::class.java]
        mTimelineViewModel.setContext(requireContext())
        mListItem = mTimelineViewModel.getListItemTimeLine()
        mTimelineViewModel.getListItemDetail()
        mTimelineManager = TimeLineManager(mTimelineViewModel, binding)


        binding.appBarTimeLine.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()
            binding.textCollapsingToolbar.alpha = (1 - percentage * 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
