package com.example.galaxymoment.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.galaxymoment.adapter.PagerAdapterRCV
import com.example.galaxymoment.data.MediaItems
import com.example.galaxymoment.databinding.FragmentViewPager2Binding
import com.example.galaxymoment.utils.Constants.DEFAULT
import com.example.galaxymoment.utils.Constants.SWIPE_PRE_POS
import com.example.galaxymoment.utils.Constants.SWIPE_NEX_POS
import com.example.galaxymoment.viewmodel.VideoViewModel

class ViewPager2Fragment : Fragment() {
    private lateinit var viewModel: VideoViewModel
    private lateinit var viewPager : ViewPager2
    private var _binding: FragmentViewPager2Binding? = null
    private val binding get() = _binding!!
    private var oldPosition = -1
    private var oldOffSet = 0
    private var checkTypeSwipe = DEFAULT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewPager2Binding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[VideoViewModel::class.java]
        viewPager = binding.fragmentViewPager2
        val adapter = PagerAdapterRCV(viewModel.videoList.value as List<MediaItems>)
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewModel.videoList.observe(viewLifecycleOwner) {
        }
        viewPager.setCurrentItem(viewModel.currentPosPager.value!!, false)
        viewPager.offscreenPageLimit = 1
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.videoView.setOnCompletionListener {
            binding.videoView.start()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setCurrentPosPager(position)
                playVideo(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.i("dongdong","ViewPager2Fragment onPageScrollStateChanged state = $state position = $oldPosition")
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                calculateOffsetVideo(position,positionOffsetPixels)
                if (checkTypeSwipe == DEFAULT) {
                    Log.i("dongdong", "setURI checkTypeSwipe : SWIPE_TO_RIGHT")
                    oldPosition = position
                    playVideo(position)
                }
            }
        })
    }

    private var oldSwipeTypeCheck = DEFAULT
    private var offSet = 0.0f
    private var offSetTmp = 0.0f
    private fun calculateOffsetVideo(position: Int, positionOffsetPixels: Int) {
        if (position == viewModel.currentPosPager.value!!) {
            if (positionOffsetPixels > oldOffSet) {
                //swipe to right
                oldOffSet = positionOffsetPixels
                checkTypeSwipe = SWIPE_NEX_POS
                offSet = (-positionOffsetPixels).toFloat()
                binding.videoView.translationX = offSet
            } else if (positionOffsetPixels < oldOffSet) {
                //swipe to left
                oldOffSet = positionOffsetPixels
                checkTypeSwipe = SWIPE_PRE_POS
                offSet = (binding.videoView.width - positionOffsetPixels).toFloat()
                binding.videoView.translationX = offSet
                binding.fragmentViewPager2.translationX
            }
        } else if (position + 1 == viewModel.currentPosPager.value!!) {
            //swipe to left but difference position
            oldOffSet = positionOffsetPixels
            checkTypeSwipe = SWIPE_PRE_POS
            offSet = (binding.videoView.width + binding.videoView.width - positionOffsetPixels).toFloat()
            binding.videoView.translationX = offSet
            binding.fragmentViewPager2.translationX
        }
        if (oldSwipeTypeCheck == SWIPE_PRE_POS && checkTypeSwipe == SWIPE_NEX_POS) {
            offSetTmp = 0.0f
        } else if (oldSwipeTypeCheck == SWIPE_NEX_POS && checkTypeSwipe == SWIPE_PRE_POS) {
            offSetTmp = -binding.videoView.width.toFloat()
        }
        binding.videoView.translationX += offSetTmp
        oldSwipeTypeCheck = checkTypeSwipe
    }

    fun playVideo(position : Int){
        binding.videoView.setVideoURI(Uri.parse(viewModel.videoList.value!![position].path))
        binding.videoView.start()
    }
}