package com.example.galaxymoment.manager

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.galaxymoment.R
import com.example.galaxymoment.activity.GalleryActivity
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemViewPagerBinding
import com.example.galaxymoment.fragment.SearchFragment
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.viewmodel.DetailViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MoreInfoManager(
    private val mMediaItem: MediaItems,
    private val binding: ItemViewPagerBinding,
    private val viewModel: DetailViewModel,
) {
    private val mHorizontalScrollView = binding.moreInfoLayout.horizontalScrollView
    private lateinit var chipGroup: ChipGroup
    init {
        initView()
    }

    private fun initView() {
        setDataMoreInfo()
    }

    @SuppressLint("SetTextI18n")
    private fun setDataMoreInfo() {
        binding.moreInfoLayout.moreInfoDate.text = LogicUtils.convertLongToDate(mMediaItem.date)
        binding.moreInfoLayout.moreInfoPath.text = LogicUtils.formatMoreInfoPath(mMediaItem.path)
        binding.moreInfoLayout.moreInfoSize.text = mMediaItem.size.toString()
        binding.moreInfoLayout.moreInfoResolution.text = mMediaItem.resolution
        val codecFps = LogicUtils.getCodecAndFps(mMediaItem.uri, binding.moreInfoView.context)
        binding.moreInfoLayout.moreInfoCodec.text = codecFps.split("/")[0]
        binding.moreInfoLayout.moreInfoFps.text = codecFps.split("/")[1] + " fps"
        chipGroup = binding.moreInfoLayout.chipGroup
        setUpChipView()
    }

    private fun setUpChipView() {
        chipGroup.removeAllViews()
        for (tag in mMediaItem.tagList){
            val chip = Chip(binding.moreInfoLayout.root.context)
            chip.text = tag
            chip.setChipBackgroundColorResource(R.color.white)
            chip.setChipStrokeColorResource(R.color.black)
            chip.setTextColor(chip.context.getColor(R.color.black))
            chip.chipStrokeWidth = 10f
            chipGroup.addView(chip)
            chip.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("tagsearch", tag)
                val fragment = SearchFragment()
                fragment.arguments = bundle
                fragment.setDetailViewModel(viewModel)
                val transaction = (binding.moreInfoLayout.root.context as GalleryActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}