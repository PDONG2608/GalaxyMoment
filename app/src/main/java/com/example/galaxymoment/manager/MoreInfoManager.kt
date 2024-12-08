package com.example.galaxymoment.manager

import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.FragmentDetailBinding
import com.example.galaxymoment.utils.AnimationHelper
import com.example.galaxymoment.utils.DateUtils
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
        buttonClickMoreInfo()

    }

    private fun setDataMoreInfo() {
        binding.moreInfoLayout.moreInfoDate.text = DateUtils.convertLongToDate(mMediaItem.date)
        binding.moreInfoLayout.moreInfoPath.text = LogicUtils.formatMoreInfoPath(mMediaItem.path)
    }

    private fun buttonClickMoreInfo() {
        binding.buttonOpenMoreinfo.setOnClickListener {
            if (mDetailViewModel.isShowMoreInfo.value == false) {
                AnimationHelper.makeAnimationChangeHeight(binding.moreInfoView, 0, 400, 400)
            } else {
                AnimationHelper.makeAnimationChangeHeight(binding.moreInfoView, 400, 0, 400)
            }
            mDetailViewModel.setIsShowMoreInfo(mDetailViewModel.isShowMoreInfo.value!!.not())
        }
    }
}