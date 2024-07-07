package com.example.galaxymoment.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.galaxymoment.databinding.FragmentMediaBinding
import com.example.galaxymoment.utils.Constants.ARG_MEDIA_PATH

class MediaFragment : Fragment() {
    private var _binding: FragmentMediaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        val view = binding.root

        val mediaPath = arguments?.getString(ARG_MEDIA_PATH) ?: ""

        Glide.with(this)
            .load(Uri.parse(mediaPath))
            .frame(0)
            .centerCrop()
            .into(binding.mediaImageView)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(mediaPath: String) = MediaFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MEDIA_PATH, mediaPath)
            }
        }
    }
}