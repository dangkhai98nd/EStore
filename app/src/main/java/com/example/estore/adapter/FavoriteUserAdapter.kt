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
import com.example.estore.R
import com.example.estore.databinding.ItemBrowseBinding
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.ui.DetailActivity
import com.example.estore.viewmodel.BrowseViewModel

class FavoriteUserAdapter(
    private val mContext: Context,
    private val listFavorite: List<String>
) : RecyclerView.Adapter<FavoriteUserAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_browse,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(position)
    }

    inner class ItemHolder(
        private val itemBrowseBinding: ItemBrowseBinding
    ) : RecyclerView.ViewHolder(itemBrowseBinding.root) {

        fun bind(position: Int) {
            val index = database.indexOfFirst { it.id == listFavorite[position] }
            if (index >= 0) {
                val product = database[index]
                itemBrowseBinding.browseViewModel = BrowseViewModel(product, itemBrowseBinding.root.context)
            }
        }
    }

}