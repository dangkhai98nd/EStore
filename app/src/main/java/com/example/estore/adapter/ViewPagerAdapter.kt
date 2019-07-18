package com.example.estore.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.estore.fragment.BrowseFragment
import com.example.estore.fragment.HotFragment

class ViewPagerAdapter(
    fm: FragmentManager,
    private val mContext: Context
) : FragmentPagerAdapter(fm) {
    companion object {
        val NUMBER_PAGE: Int = 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BrowseFragment()
            1 -> HotFragment()
            2 -> BrowseFragment()
            else -> HotFragment()
        }
    }

    override fun getCount(): Int = NUMBER_PAGE

//    fun createTabView(position: Int, isSelected: Boolean = false): View {
//        val view = LayoutInflater.from(mContext).inflate(R.layout.tab_custom,null)
//        return view
//    }
}