package com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.rsrafprojekat2david_maloparac_rn9519.R
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment.CategoryFragment
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment.SavedFragment
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment.SavedListFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> CategoryFragment()
            else -> SavedListFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> context.getString(R.string.categories)
            else -> context.getString(R.string.saved)
        }
    }

}