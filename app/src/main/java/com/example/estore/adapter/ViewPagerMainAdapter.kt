package com.example.estore.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.estore.fragment.BrowseFragment
import com.example.estore.fragment.CartFragment
import com.example.estore.fragment.HotFragment
import com.example.estore.fragment.ProfileFragment

class ViewPagerMainAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    val NUMBER_PAGE: Int = 4


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BrowseFragment()
            1 -> HotFragment()
            2 -> CartFragment()
            else -> ProfileFragment()

        }
    }

    override fun getCount(): Int = NUMBER_PAGE


//    fun createTabView(position: Int, isSelected: Boolean = false): View {
//        val view = LayoutInflater.from(mContext).inflate(R.layout.tab_custom,null)
//        return view
//    }
}