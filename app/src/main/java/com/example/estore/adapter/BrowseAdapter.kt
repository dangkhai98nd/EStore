package com.example.estore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.databinding.ItemBrowseBinding
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.ui.DetailActivity
import com.example.estore.viewmodel.BrowseViewModel
import kotlinx.android.extensions.LayoutContainer

class BrowseAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<BrowseAdapter.ItemViewHolder>() {

    private var products : List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_browse,parent,false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(products[position])
    }

    inner class ItemViewHolder(
        private val itemBrowseBinding: ItemBrowseBinding
    ) : RecyclerView.ViewHolder(itemBrowseBinding.root){

        fun bind(product: Product) {
            itemBrowseBinding.browseViewModel = BrowseViewModel(product,itemBrowseBinding.root.context)
        }
    }

    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
}