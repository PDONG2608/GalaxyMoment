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
import com.google.android.material.chip.Chip

class SearchFragment : Fragment() {
    private var firstTagSearch: String? = null
    private var mMediaItems = ArrayList<MediaItems>()
    private var mTimeLineItem = ArrayList<TimeLineType>()
    private var viewModel: DetailViewModel? = null
    private val chipLabels = arrayOf("happy", "sad", "lonely", "smile", "angry", "interested")
    private val _binding : FragmentSearchBinding by lazy { FragmentSearchBinding.bind(requireView()) }
    private val binding get() = _binding

    fun setDetailViewModel(detailViewModel: DetailViewModel) {
        viewModel = detailViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstTagSearch = it.getString("tagsearch")
            mMediaItems = viewModel!!.getTreeMapTag()[firstTagSearch]!!
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
        initChipView()
        loadRecycleView(mTimeLineItem)
    }

    private fun initChipView() {
        binding.chipGroup.removeAllViews()
        binding.chipGroupSearch.removeAllViews()
        for (tagString in chipLabels) {
            val chip = Chip(requireContext())
            chip.text = tagString
            if(tagString == firstTagSearch) {
                binding.chipGroupSearch.addView(chip)
            }else {
                binding.chipGroup.addView(chip)
            }
            chip.setOnClickListener {
                if(chip.parent == binding.chipGroup) {
                    binding.chipGroup.removeView(chip)
                    binding.chipGroupSearch.addView(chip)
                } else if( chip.parent == binding.chipGroupSearch) {
                    binding.chipGroupSearch.removeView(chip)
                    binding.chipGroup.addView(chip)
                }
                reloadData()
            }
        }
    }

    private fun reloadData() {
        val selectedTag = ArrayList<String>()
        for (i in 0 until binding.chipGroupSearch.childCount) {
            val chip = binding.chipGroupSearch.getChildAt(i) as Chip
            selectedTag.add(chip.text.toString())
        }
        val newLists = viewModel!!.getListItemByTags(selectedTag)
        loadRecycleView(newLists)
    }

    private fun loadRecycleView(newLists: ArrayList<TimeLineType>) {
        val searchAdapter = SearchAdapter(newLists)
        val mLayoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerView.adapter = searchAdapter
        binding.recyclerView.layoutManager = mLayoutManager
        LogicUtils.setSpanSize(searchAdapter, mLayoutManager, 4)
    }
}