package com.example.estore.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.estore.R
import com.example.estore.adapter.FilterAdapter
import com.example.estore.adapter.ViewPagerMainAdapter
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.utils.CubeInRotationTransformation
import com.example.estore.utils.RecyclerViewOnClickListener
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var preMenuItem: MenuItem? = null
    private var filterAdapter: FilterAdapter? = null
    private var viewPagerMainAdapter: ViewPagerMainAdapter? = null
    private var paramsAppBarLayout: AppBarLayout.LayoutParams? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DatabaseEstore.getDatabase(this, "meCSq8W5SKT8EQRITQhDHuoTcLG2")
        Handler().postDelayed({
            Log.e("static", "${database[0].name}")
            Log.e("static user", "${userEstore?.userName}")
        }, 4000)
        paramsAppBarLayout = ctlMain.layoutParams as AppBarLayout.LayoutParams
        initToolbar()
        initView()

    }


    private fun initToolbar() {
        ic_search.setOnClickListener {
            ic_search.visibility = View.GONE
            tvTitle.visibility = View.GONE
            groupSearch.visibility = View.VISIBLE
        }
        ic_close.setOnClickListener {
            ic_search.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            groupSearch.visibility = View.GONE
        }
        tvSearch.setOnClickListener {

        }
    }

    private fun initView() {
        setupRvFilter()
        setupSelectFilter()
        setupNavigation()
    }

    private fun setupSelectFilter() {
        rvFilter.addOnItemTouchListener(
            RecyclerViewOnClickListener(
                this, object : RecyclerViewOnClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        filterAdapter?.setSelectedPosition(position)

                    }

                })
        )
    }

    private fun setupNavigation() {
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_browse -> vpMain.currentItem = 0
                R.id.navigation_hot -> vpMain.currentItem = 1
                R.id.navigation_cart -> vpMain.currentItem = 2
                R.id.navigation_profile -> vpMain.currentItem = 3
            }
            false
        }

        vpMain.addOnPageChangeListener(this)
        viewPagerMainAdapter = ViewPagerMainAdapter(supportFragmentManager)
        vpMain.adapter = viewPagerMainAdapter
        vpMain.setPageTransformer(true, CubeInRotationTransformation())
    }

    private fun setupRvFilter() {
        filterAdapter = FilterAdapter(this@MainActivity)
        rvFilter.adapter = filterAdapter
        rvFilter.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (preMenuItem != null) {
            preMenuItem?.isChecked = false
        } else {
            navigation.menu.getItem(0).isChecked = true
        }
        navigation.menu.getItem(position).isChecked = true
        preMenuItem = navigation.menu.getItem(position)
        when (position) {
            0 -> {
                paramsAppBarLayout?.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL +
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                tvTitle.text = "BROWSE"
                ic_search.visibility = ImageView.VISIBLE
                ic_paymentconfirmed.visibility = ImageView.GONE
                rvFilter.visibility = RecyclerView.VISIBLE
            }
            1 -> {
                paramsAppBarLayout?.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL +
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                tvTitle.text = "HOT"
                ic_search.visibility = ImageView.VISIBLE
                ic_paymentconfirmed.visibility = ImageView.GONE
                rvFilter.visibility = RecyclerView.VISIBLE
            }
            2 -> {
                paramsAppBarLayout?.scrollFlags = 0
                tvTitle.text = "CART"
                ic_search.visibility = ImageView.GONE
                ic_paymentconfirmed.visibility = ImageView.VISIBLE
                rvFilter.visibility = RecyclerView.GONE
            }
            else -> {
                paramsAppBarLayout?.scrollFlags = 0
                tvTitle.text = "PROFILE"
                ic_search.visibility = ImageView.GONE
                ic_paymentconfirmed.visibility = ImageView.GONE
                rvFilter.visibility = RecyclerView.GONE
            }
        }
        ctlMain.layoutParams = paramsAppBarLayout
    }

}
