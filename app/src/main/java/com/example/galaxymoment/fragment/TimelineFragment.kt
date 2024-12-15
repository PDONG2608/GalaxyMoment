package com.example.galaxymoment.fragment


import android.os.Bundle
import android.transition.ArcMotion
import android.transition.ChangeBounds
import android.transition.ChangeClipBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
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
        binding.recyclerView.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    binding.recyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                    startPostponedEnterTransition()
                    return true
                }
            }
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getRCV() : RecyclerView{
        return binding.recyclerView
    }
}
