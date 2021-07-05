package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.rsrafprojekat2david_maloparac_rn9519.R
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.adapter.MainPagerAdapter
import com.google.android.material.tabs.TabLayout

class MainFragment : Fragment(R.layout.fragment_main) {

    private var viewPager : ViewPager? = null
    private var tabLayout : TabLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initTabs()
    }

    private fun initView() {
        viewPager = requireView().findViewById(R.id.viewPager)
        tabLayout = requireView().findViewById(R.id.tabLayout)
    }

    private fun initTabs() {
        viewPager!!.adapter = activity?.let { MainPagerAdapter(childFragmentManager, it) }
        tabLayout!!.setupWithViewPager(viewPager)
    }
}