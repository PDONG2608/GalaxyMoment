package com.example.galaxymoment.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galaxymoment.R
import com.example.galaxymoment.adapter.BaseAdapter
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
    private val chipLabels = arrayOf("happy", "sad", "lonely", "smile", "angry", "interested", "good", "bad", "excited", "boring", "funny", "crazy")
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

    @SuppressLint("ResourceType")
    private fun initChipView() {
        binding.chipGroup.removeAllViews()
        binding.chipGroupSearch.removeAllViews()
        for (tagString in chipLabels) {
            val chip = Chip(requireContext())
            chip.text = tagString
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setChipStrokeColorResource(R.color.black)
            chip.setTextColor(resources.getColor(R.color.black))
            chip.chipStrokeWidth = 10f
            if(tagString == firstTagSearch) {
                binding.chipGroupSearch.addView(chip)
            }else {
                binding.chipGroup.addView(chip)
            }
            chip.setOnClickListener {
                handleCaseSearch(chip)
            }
        }
    }

    private fun handleCaseSearch(chip: Chip) {
        if(chip.parent == binding.chipGroup) {
            binding.chipGroup.removeView(chip)
            binding.chipGroupSearch.addView(chip)
        } else if( chip.parent == binding.chipGroupSearch) {
            binding.chipGroupSearch.removeView(chip)
            binding.chipGroup.addView(chip)
        }
        if (binding.chipGroupSearch.childCount == 0) {
            binding.searchView.hint = "Search"
        } else {
            binding.searchView.hint = ""
        }
        reloadData()
    }

    private fun reloadData() {
        val selectedTag = ArrayList<String>()
        for (i in 0 until binding.chipGroupSearch.childCount) {
            val chip = binding.chipGroupSearch.getChildAt(i) as Chip
            selectedTag.add(chip.text.toString())
        }
        val newLists = viewModel!!.getListItemByTags(selectedTag)
        (binding.recyclerView.adapter as BaseAdapter<*>).updateData(newLists)
    }

    private fun loadRecycleView(newLists: ArrayList<TimeLineType>) {
        val searchAdapter = SearchAdapter(newLists)
        val mLayoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerView.adapter = searchAdapter
        binding.recyclerView.layoutManager = mLayoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        LogicUtils.setSpanSize(searchAdapter, mLayoutManager, 4)

        //load recycle view for edittext
        val adapter = ArrayAdapter(requireContext(), R.layout.search_drop_down_item, chipLabels)
        binding.searchView.threshold = 1
        binding.searchView.setAdapter(adapter)
        binding.searchView.setTextColor(Color.BLACK)
        //click a item of dropdown

        binding.searchView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            binding.searchView.setText(selectedItem)
            for (i in 0 until binding.chipGroup.childCount) {
                if (binding.chipGroup.getChildAt(i) is Chip) {
                    val chip = binding.chipGroup.getChildAt(i) as Chip
                    if (chip.text == selectedItem) {
                        handleCaseSearch(chip)
                    }
                }
            }
            binding.searchView.setText("")
        }
    }
}