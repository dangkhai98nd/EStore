package com.example.estore

import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var preMenuItem : MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
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
//        tabLayout.setupWithViewPager(viewPager)
//        tabLayout.getTabAt(0)?.customView = viewPagerAdapter.createTabView(0, true)
//        tabLayout.getTabAt(1)?.customView = viewPagerAdapter.createTabView(1)
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
