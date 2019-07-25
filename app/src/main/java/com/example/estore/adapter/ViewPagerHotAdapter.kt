package com.example.estore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.estore.R
import com.example.estore.model.Product
import com.example.estore.ui.DetailActivity
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.extensions.LayoutContainer

class ViewPagerHotAdapter(
    private val mContext: Context,
    private val isSmall: Boolean
) : PagerAdapter() {
    private val mInflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var products: List<Product> = listOf()
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return when (isSmall) {
            true -> {
                val rootView = mInflater.inflate(R.layout.item_small_image_product, container, false)
                val holder = ItemSmallViewHolder(rootView)
                holder.bind(position)
                container.addView(rootView)
                rootView
            }
            else -> {
                val rootView = mInflater.inflate(R.layout.item_large_image_product, container, false)
                val holder = ItemLargeViewHolder(rootView)
                holder.bind(position)
                container.addView(rootView)
                rootView
            }
        }

    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun getCount(): Int = products.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    inner class ItemSmallViewHolder(
        override val containerView: View
    ) : LayoutContainer {
        private val ivProductSmallHot = containerView.findViewById<ImageView>(R.id.ivProductSmallHot)
        fun bind(position: Int) {
            Glide.with(containerView)
                .load(products[position].photoDark)
                .into(ivProductSmallHot)
            ivProductSmallHot.setOnClickListener {
                val intent = Intent(mContext, DetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("position", position)
                mContext.startActivity(intent)
            }
        }
    }

    inner class ItemLargeViewHolder(
        override val containerView: View
    ) : LayoutContainer {
        private val ivProductLargeCart = containerView.findViewById<ImageView>(R.id.ivProductLargeCart)
        fun bind(position: Int) {
            Glide.with(containerView)
                .load(products[position].photoDark)
                .apply(bitmapTransform(BlurTransformation(10, 1)))
                .into(ivProductLargeCart)
        }
    }

    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

}