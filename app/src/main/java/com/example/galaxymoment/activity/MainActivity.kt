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
import com.example.galaxymoment.databinding.ActivityMainBinding
import com.example.galaxymoment.fragment.TimelineFragment
import com.example.galaxymoment.viewmodel.VideoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: VideoViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[VideoViewModel::class.java]
        checkPermissions()
        initTimeLineFragment()
    }

    private fun initTimeLineFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, TimelineFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissions() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("dongdong","hasPermission")
                viewModel.loadVideos(this)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_VIDEO) -> {
                // Show an explanation to the user
            }
            else -> {
                Log.i("dongdong","noPermission")
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.loadVideos(this)
        } else {
            // Permission denied
        }
    }
}
