package com.example.galaxymoment.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.galaxymoment.R
import com.example.galaxymoment.callback.NavigationFragmentListener
import com.example.galaxymoment.data.FragmentType
import com.example.galaxymoment.databinding.ActivityMainBinding
import com.example.galaxymoment.fragment.TimelineFragment
import com.example.galaxymoment.viewmodel.TimelineViewModel

class GalleryActivity : AppCompatActivity() , NavigationFragmentListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mTimelineViewModel: TimelineViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mTimelineViewModel = ViewModelProvider(this)[TimelineViewModel::class.java]
        checkPermissions()
    }

    private fun initTimeLineFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, TimelineFragment())
        transaction.commit()
    }

    override fun onResume() {
        super.onResume()
        initTimeLineFragment()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("dongdong","hasPermission")
                initTimeLineFragment()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_VIDEO) -> {
                // Show an explanation to the user
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            initTimeLineFragment()
        } else {
            // Permission denied
        }
    }

    override fun navigateToFragment(fragmentType: FragmentType) {
        TODO("Not yet implemented")
    }
}
