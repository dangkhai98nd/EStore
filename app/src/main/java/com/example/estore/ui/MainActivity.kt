package com.example.estore.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.estore.R
import com.example.estore.adapter.FilterAdapter
import com.example.estore.adapter.ViewPagerMainAdapter
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.databaseFilter
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.utils.CubeInRotationTransformation
import com.example.estore.utils.RecyclerViewOnClickListener
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var preMenuItem: MenuItem? = null
    private var filterAdapter: FilterAdapter? = null
    private var viewPagerMainAdapter: ViewPagerMainAdapter? = null
    private var paramsAppBarLayout: AppBarLayout.LayoutParams? = null
    private lateinit var databaseRef: DatabaseReference
    private var positionSort = 0
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val listProduct = mutableListOf<Product>()
                for(product in p0.children){
                    product.getValue(Product::class.java)?.let { listProduct.add(it) }
                }
                database.clear()
                database.addAll(listProduct)
                when(positionSort){
                    0 -> {
                        databaseFilter.value = database
                    }
                    1 -> {
                        databaseFilter.value = database.filter { it.trending == true }
                    }
                    2 -> {
                        databaseFilter.value = sortingNew()
                    }
                    3 -> {
                        databaseFilter.value = sortingPrice()
                    }
                }
            }
        })

        paramsAppBarLayout = ctlMain.layoutParams as AppBarLayout.LayoutParams
        initToolbar()
        initView()

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            val intent = Intent(this@MainActivity, SignInActivity::class.java)
            database.clear()
            userEstore.value = null
            DatabaseEstore.databaseFilter.value = listOf()
            DatabaseEstore.listUser.clear()
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
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
            if (edtSearch.text.toString() != "") {
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("keyword", edtSearch.text.toString())
                startActivity(intent)
            }
        }
    }

    private fun initView() {
        setupSelectFilter()
        setupRvFilter()
        setupNavigation()
    }

    private fun setupSelectFilter() {
        rvFilter.addOnItemTouchListener(
            RecyclerViewOnClickListener(
                this, object : RecyclerViewOnClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        filterAdapter?.setSelectedPosition(position)
                        Log.e("position filter","$position")
                        when(position)
                        {
                            0 -> {
                                databaseFilter.value = database
                                positionSort = 0
                            }
                            1 -> {
                                databaseFilter.value = database.filter { it.trending == true }
                                positionSort = 1
                            }
                            2 -> {
                                databaseFilter.value = sortingNew()
                                positionSort = 2
                            }
                            3 -> {
                                databaseFilter.value = sortingPrice()
                                positionSort = 3
                            }
                        }
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
                tvTitle.text = StringBuilder().append("BROWSE")
                ic_search.visibility = ImageView.VISIBLE
                ic_paymentconfirmed.visibility = ImageView.GONE
                rvFilter.visibility = RecyclerView.VISIBLE
            }
            1 -> {
                paramsAppBarLayout?.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL +
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                tvTitle.text = StringBuilder().append("HOT")
                ic_search.visibility = ImageView.VISIBLE
                ic_paymentconfirmed.visibility = ImageView.GONE
                rvFilter.visibility = RecyclerView.VISIBLE
            }
            2 -> {
                paramsAppBarLayout?.scrollFlags = 0
                tvTitle.text = StringBuilder().append("CART")
                ic_search.visibility = ImageView.GONE
                ic_paymentconfirmed.visibility = ImageView.VISIBLE
                rvFilter.visibility = RecyclerView.GONE
            }
            else -> {
                paramsAppBarLayout?.scrollFlags = 0
                tvTitle.text = StringBuilder().append("PROFILE")
                ic_search.visibility = ImageView.GONE
                ic_paymentconfirmed.visibility = ImageView.GONE
                rvFilter.visibility = RecyclerView.GONE
            }
        }
        ctlMain.layoutParams = paramsAppBarLayout
        tvTitle.visibility = View.VISIBLE
        groupSearch.visibility = View.GONE
    }

    fun sortingNew(): MutableList<Product> {
        var listNewSort = mutableListOf<Product>()
        listNewSort.addAll(database)
        val size = database.size

        var maxidx: Int
        for(i in 0 until size){
            maxidx = i
            for(j in i+1 until size){
                if(listNewSort[j].time!! > listNewSort[maxidx].time!!){
                    maxidx = j
                }
                listNewSort[maxidx] = listNewSort[i].also { listNewSort[i] = listNewSort[maxidx] }
            }
        }
        return listNewSort
    }

    fun sortingPrice(): MutableList<Product>{
        val listPriceSort = mutableListOf<Product>()
        listPriceSort.addAll(database)
        val size = database.size

        var minidx: Int
        for(i in 0 until size){
            minidx = i
            for(j in i+1 until size){
                if(listPriceSort[j].price!! > listPriceSort[minidx].price!!){
                    minidx = j
                }
                listPriceSort[minidx] = listPriceSort[i].also { listPriceSort[i] = listPriceSort[minidx] }
            }
        }
        return listPriceSort
    }

}
