package com.example.galaxymoment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.galaxymoment.databinding.ActivityDetailBinding
import com.example.galaxymoment.manager.FilmStripManager
import com.example.galaxymoment.manager.ViewPagerManager
import com.example.galaxymoment.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var mViewPagerManager: ViewPagerManager
    private lateinit var mFilmStripManager: FilmStripManager
    private lateinit var mDetailViewModel: DetailViewModel
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        mDetailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        mDetailViewModel.setContext(this)
        mViewPagerManager = ViewPagerManager(mDetailViewModel, binding, intent.extras)
        mFilmStripManager = FilmStripManager(mDetailViewModel, binding)
    }
}