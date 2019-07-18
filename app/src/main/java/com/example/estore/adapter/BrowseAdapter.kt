package com.example.estore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.estore.R
import com.example.estore.model.Product
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

        fun bind(product : Product) {
            Glide.with(mContext)
                .load(product.photoDark)
                .into(ivProductBrowse)
            tvProductNameBrowse.text = product.name
            if (product.trending == true)
                clTrendingBrowse.visibility = ConstraintLayout.VISIBLE
            else clTrendingBrowse.visibility = ConstraintLayout.GONE
            tvProductPriceBrowse.text = "\$" + product?.price.toString()
            tvLikeCounterBrowse.text = "${product?.likeCounter} likes"
            tvCommentCounterBrowse.text = "${product?.commentCounter} comments"
        }
    }
}