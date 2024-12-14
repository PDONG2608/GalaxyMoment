package com.example.galaxymoment.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.R
import com.example.galaxymoment.adapter.SearchAdapter
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.data.TimeLineType
import com.example.galaxymoment.databinding.FragmentSearchBinding
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.viewmodel.DetailViewModel

class SearchFragment : Fragment() {
    private var tagSearch: String? = null
    private var mMediaItems = ArrayList<MediaItems>()
    private var mTimeLineItem = ArrayList<TimeLineType>()
    private var mDetailViewModel: DetailViewModel? = null
    private val _binding : FragmentSearchBinding by lazy { FragmentSearchBinding.bind(requireView()) }
    private val binding get() = _binding

    fun setDetailViewModel(detailViewModel: DetailViewModel) {
        mDetailViewModel = detailViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tagSearch = it.getString("tagsearch")
            mMediaItems = mDetailViewModel!!.getTreeMapTag()[tagSearch]!!
            mTimeLineItem = LogicUtils.formatToTypeTimeLine(mMediaItems)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchAdapter = SearchAdapter(mTimeLineItem)
        val mLayoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerView.adapter = searchAdapter
        binding.recyclerView.layoutManager = mLayoutManager
        LogicUtils.setSpanSize(searchAdapter, mLayoutManager, 4)
    }


}