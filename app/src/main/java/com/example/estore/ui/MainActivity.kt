package com.example.estore.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.estore.R
import com.example.estore.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var preMenuItem : MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initView()
    }

    private fun initToolbar() {
        ic_search.setOnClickListener {
            groupNormal.visibility = View.GONE
            groupSearch.visibility = View.VISIBLE
        }
        ic_close.setOnClickListener{
            groupNormal.visibility = View.VISIBLE
            groupSearch.visibility = View.GONE
        }
        tvSearch.setOnClickListener {

        }
    }

    private fun initView() {
        navigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.navigation_browse -> viewPager.currentItem = 0
                R.id.navigation_hot -> viewPager.currentItem = 1
                R.id.navigation_cart -> viewPager.currentItem = 2
                R.id.navigation_profile -> viewPager.currentItem = 3
            }
            false
        }
        viewPager.addOnPageChangeListener(this)
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, this)
        viewPager.adapter = viewPagerAdapter
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (preMenuItem != null)
        {
            preMenuItem?.isChecked = false
        }
        else {
            navigation.menu.getItem(0).isChecked = true
        }
        navigation.menu.getItem(position).isChecked = true
        preMenuItem = navigation.menu.getItem(position)

    }

}
