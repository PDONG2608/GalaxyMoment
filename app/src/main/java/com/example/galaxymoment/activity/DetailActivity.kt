package com.example.galaxymoment.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ArcMotion
import android.transition.ChangeBounds
import android.transition.ChangeClipBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.galaxymoment.databinding.ActivityDetailBinding
import com.example.galaxymoment.manager.FilmStripManager
import com.example.galaxymoment.manager.ViewPagerManager
import com.example.galaxymoment.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private var stringPath: String? = null
    private lateinit var mViewPagerManager: ViewPagerManager
    private lateinit var mFilmStripManager: FilmStripManager
    private lateinit var mDetailViewModel: DetailViewModel
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val mHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stringPath = intent.getStringExtra("timeLineUri")
        window.sharedElementEnterTransition = TransitionSet().apply {
            interpolator = OvershootInterpolator(0.7f)
            ordering = TransitionSet.ORDERING_TOGETHER
            addTransition(ChangeBounds().apply { pathMotion = ArcMotion() })
            addTransition(ChangeTransform())
            addTransition(ChangeClipBounds())
            addTransition(ChangeImageTransform())
        }

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        Log.i("dongdong", "DetailActivity initView  = $stringPath")
        initAnimation(stringPath)

        mDetailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        mDetailViewModel.setContext(this)
        mViewPagerManager = ViewPagerManager(mDetailViewModel, binding, stringPath)
        mFilmStripManager = FilmStripManager(mDetailViewModel, binding)

        binding.favoriteIcon.setOnClickListener {
            it.isSelected = it.isSelected.not()
        }
    }

    private fun initAnimation(stringPath: String?) {
        binding.imageAnim.visibility = android.view.View.VISIBLE
        Glide.with(this).load(stringPath).frame(0).into(binding.imageAnim)
        binding.imageAnim.transitionName = stringPath
        mHandler.removeCallbacksAndMessages(null)
        mHandler.postDelayed({
            binding.imageAnim.visibility = android.view.View.GONE
        }, 500)
    }
}