package com.example.estore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.estore.R
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.databaseFilter
import com.example.estore.model.Product
import com.example.estore.utils.CubeInRotationTransformation
import com.example.estore.utils.DepthTransformation
import kotlinx.android.extensions.LayoutContainer

class HotAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<HotAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hot, parent, false))
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        when(position)
        {
            0 -> holder.bind(databaseFilter.value?.filter { it.brand == "apple" } ?: databaseFilter.value ?: database)
            1 -> holder.bind(databaseFilter.value?.filter { it.brand == "samsung" } ?: databaseFilter.value ?: database)
            2 -> holder.bind(databaseFilter.value?.filter { it.brand == "xiaomi" } ?: databaseFilter.value ?: database)
            3 -> holder.bind( databaseFilter.value ?: database)
        }
    }

    inner class ItemViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val vpSmallProductHot = containerView.findViewById<ViewPager>(R.id.vpSmallProductHot)
        private val ivBackwardtickHot = containerView.findViewById<ImageView>(R.id.ivBackwardtickHot)
        private val ivForwardtickHot = containerView.findViewById<ImageView>(R.id.ivForwardtickHot)
        private val tvProductNameHot = containerView.findViewById<TextView>(R.id.tvProductNameHot)
        private val vpLargeProductHot = containerView.findViewById<ViewPager>(R.id.vpLargeProductHot)
        private var vpSmallProductHotAdapter: ViewPagerHotAdapter? = null
        private var vpLargeProductHotAdapter: ViewPagerHotAdapter? = null


        fun bind(products : List<Product>) {
            setupViewPagerLarge(products)
            setupViewPagerSmall(products)
            tvProductNameHot.text = products[0].name
            ivBackwardtickHot.setOnClickListener {
                vpSmallProductHot.currentItem -= 1
            }
            ivForwardtickHot.setOnClickListener {
                vpSmallProductHot.currentItem += 1
            }
        }

        private fun setupViewPagerSmall(products : List<Product>) {
            vpSmallProductHot.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                private var mScrollState = ViewPager.SCROLL_STATE_IDLE
                override fun onPageScrollStateChanged(state: Int) {
                    mScrollState = state
                    if (state == ViewPager.SCROLL_STATE_IDLE)
                    {
                        vpLargeProductHot.currentItem = vpSmallProductHot.currentItem
                    }
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if (mScrollState == ViewPager.SCROLL_STATE_IDLE)
                        return
                    vpLargeProductHot.scrollTo(vpSmallProductHot.scrollX*3,vpLargeProductHot.scrollY)
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            ivBackwardtickHot.visibility = ImageView.INVISIBLE
                            ivForwardtickHot.visibility = ImageView.VISIBLE
                        }
                        products.size - 1 -> {
                            ivBackwardtickHot.visibility = ImageView.VISIBLE
                            ivForwardtickHot.visibility = ImageView.INVISIBLE
                        }
                        else -> {
                            ivBackwardtickHot.visibility = ImageView.VISIBLE
                            ivForwardtickHot.visibility = ImageView.VISIBLE
                        }
                    }
                    tvProductNameHot.text = products[position].name
                }
            })
            vpSmallProductHotAdapter = ViewPagerHotAdapter(containerView.context, true)
            vpSmallProductHot.adapter = vpSmallProductHotAdapter
            vpSmallProductHot.setPageTransformer(true, DepthTransformation())
            vpSmallProductHotAdapter?.setProducts(products)
        }

        private fun setupViewPagerLarge(products : List<Product>) {
            vpLargeProductHot.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {

                }
            })
            vpLargeProductHotAdapter = ViewPagerHotAdapter(containerView.context, false)
            vpLargeProductHot.adapter = vpLargeProductHotAdapter
            vpLargeProductHotAdapter?.setProducts(products)
        }
    }
}