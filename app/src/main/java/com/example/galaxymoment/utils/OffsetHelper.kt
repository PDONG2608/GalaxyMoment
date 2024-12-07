package com.example.galaxymoment.utils

import com.example.galaxymoment.databinding.FragmentViewPager2Binding

class OffsetHelper {
    companion object {
        var oldOffSet = 0
        var checkTypeSwipe = Constants.DEFAULT
        var oldSwipeTypeCheck = Constants.DEFAULT
        var offSet = 0.0f
        var offSetTmp = 0.0f
        fun setOffSetForVideo(
            binding: FragmentViewPager2Binding,
            curPosVM: Int,
            position: Int,
            positionOffsetPixels: Int
        ) {
            if (position == curPosVM) {
                if (positionOffsetPixels > oldOffSet) {
                    oldOffSet = positionOffsetPixels
                    //swipe to right
                    checkTypeSwipe = Constants.SWIPE_NEX_POS
                    offSet = (-positionOffsetPixels).toFloat()
                } else if (positionOffsetPixels < oldOffSet) {
                    //swipe to left
                    oldOffSet = positionOffsetPixels
                    checkTypeSwipe = Constants.SWIPE_PRE_POS
                    offSet = (binding.videoView.width - positionOffsetPixels).toFloat()
                    binding.fragmentViewPager2.translationX
                }
            } else if (position + 1 == curPosVM) {
                //swipe to left but difference position
                oldOffSet = positionOffsetPixels
                checkTypeSwipe = Constants.SWIPE_PRE_POS
                offSet = (binding.videoView.width + binding.videoView.width - positionOffsetPixels).toFloat()
                binding.fragmentViewPager2.translationX
            }
            binding.videoView.translationX = offSet
            revivalOffset(binding)
        }

        private fun revivalOffset(
            binding: FragmentViewPager2Binding,
        ) {
            if (oldSwipeTypeCheck == Constants.SWIPE_PRE_POS && checkTypeSwipe == Constants.SWIPE_NEX_POS) {
                offSetTmp = 0.0f
            } else if (oldSwipeTypeCheck == Constants.SWIPE_NEX_POS && checkTypeSwipe == Constants.SWIPE_PRE_POS) {
                offSetTmp = -binding.videoView.width.toFloat()
            }
            binding.videoView.translationX += offSetTmp
            oldSwipeTypeCheck = checkTypeSwipe

        }
    }
}