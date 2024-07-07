package com.example.galaxymoment.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.galaxymoment.databinding.FragmentMediaBinding

class SinglePageFragment(private var filePath: String) : Fragment() {
    private var _binding: FragmentMediaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        val view = binding.root

        Glide.with(this)
            .load(Uri.parse(filePath))
            .frame(0)
            .fitCenter()
            .into(binding.mediaImageView)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}