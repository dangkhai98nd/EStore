package com.example.estore.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.estore.R
import com.example.estore.model.Product
import com.example.estore.ui.DetailActivity
import kotlinx.android.extensions.LayoutContainer

class BrowseAdapter (
    private val mContext : Context
) : RecyclerView.Adapter<BrowseAdapter.ItemViewHolder>() {

    private val products : MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_browse,parent,false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(products[position])
    }


    inner class ItemViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer{
        private val ivProductBrowse = containerView.findViewById<ImageView>(R.id.ivProductBrowse)
        private val tvProductNameBrowse = containerView.findViewById<TextView>(R.id.tvProductNameBrowse)
        private val clTrendingBrowse = containerView.findViewById<ConstraintLayout>(R.id.clTrendingBrowse)
        private val tvProductPriceBrowse = containerView.findViewById<TextView>(R.id.tvProductPriceBrowse)
        private val tvLikeCounterBrowse = containerView.findViewById<TextView>(R.id.tvLikeCounterBrowse)
        private val tvCommentCounterBrowse = containerView.findViewById<TextView>(R.id.tvCommentCounterBrowse)
        private val cvItemBrowse = containerView.findViewById<CardView>(R.id.cvItemBrowse)


        fun bind(product : Product) {
            Glide.with(mContext)
                .load(product.photoDark)
                .into(ivProductBrowse)
            tvProductNameBrowse.text = product.name
            if (product.trending == true)
                clTrendingBrowse.visibility = ConstraintLayout.VISIBLE
            else clTrendingBrowse.visibility = ConstraintLayout.GONE
            tvProductNameBrowse.isSelected = true
            tvProductPriceBrowse.text = "\$" + "${product.price}"
            tvLikeCounterBrowse.text = "${product.listUserLike?.size} likes"
            tvCommentCounterBrowse.text = "${product.commentCounter} comments"
            cvItemBrowse.setOnClickListener {
                val intent = Intent(mContext,DetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                mContext.startActivity(intent)
            }
        }
    }

    fun addAll(products : List<Product>) {
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    fun add(product: Product) {
        Log.e("name","${product.name}")
        this.products.add(product)
        notifyDataSetChanged()
    }
}