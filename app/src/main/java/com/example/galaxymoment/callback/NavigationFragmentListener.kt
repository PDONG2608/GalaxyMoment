package com.example.galaxymoment.callback

import com.example.galaxymoment.data.FragmentType

interface NavigationFragmentListener {
    fun navigateToFragment(fragmentType: FragmentType)
}
