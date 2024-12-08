package com.example.galaxymoment.manager

import android.annotation.SuppressLint
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.ItemViewPagerBinding
import com.example.galaxymoment.utils.LogicUtils

class MoreInfoManager(
    private val mMediaItem: MediaItems,
    private val binding: ItemViewPagerBinding,
) {

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
    }
}