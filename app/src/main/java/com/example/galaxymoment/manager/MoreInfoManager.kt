package com.example.galaxymoment.manager

import android.annotation.SuppressLint
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.FragmentDetailBinding
import com.example.galaxymoment.utils.LogicUtils
import com.example.galaxymoment.viewmodel.DetailViewModel

class MoreInfoManager(
    private val mDetailViewModel: DetailViewModel,
    private val binding: FragmentDetailBinding,
    private val position: Int
) {

    private var mMediaItem : MediaItems = mDetailViewModel.getListItemDetail()[position]

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
        val codecFps = LogicUtils.getCodecAndFps(mMediaItem.uri, mDetailViewModel.getContext())
        binding.moreInfoLayout.moreInfoCodec.text = codecFps.split("/")[0]
        binding.moreInfoLayout.moreInfoFps.text = codecFps.split("/")[1] + " fps"
    }
}