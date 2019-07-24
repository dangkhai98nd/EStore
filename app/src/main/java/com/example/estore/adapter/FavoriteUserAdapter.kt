package com.example.estore.adapter

import android.content.Context
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
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.Product

class FavoriteUserAdapter(private val mContext: Context, private val listFavorite: List<String>): RecyclerView.Adapter<FavoriteUserAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_browse, parent, false))
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(position)
    }

    inner class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ivProductBrowse = itemView.findViewById<ImageView>(R.id.ivProductBrowse)
        private val tvProductNameBrowse = itemView.findViewById<TextView>(R.id.tvProductNameBrowse)
        private val clTrendingBrowse = itemView.findViewById<ConstraintLayout>(R.id.clTrendingBrowse)
        private val tvProductPriceBrowse = itemView.findViewById<TextView>(R.id.tvProductPriceBrowse)
        private val tvLikeCounterBrowse = itemView.findViewById<TextView>(R.id.tvLikeCounterBrowse)
        private val tvCommentCounterBrowse = itemView.findViewById<TextView>(R.id.tvCommentCounterBrowse)
        private val buttonHeartBrowse = itemView.findViewById<ImageView>(R.id.buttonHeartBrowse)
        private val buttonFavoriteBrowse = itemView.findViewById<ImageView>(R.id.buttonFavoriteBrowse)

        fun bind(position: Int){
            buttonFavoriteBrowse.visibility = View.INVISIBLE
            buttonHeartBrowse.visibility = View.INVISIBLE
            val idFavorite = listFavorite[position]
            val index = database.indexOfFirst { it.id == idFavorite }
            if(index >= 0){
                val product = database[position]
                Glide.with(mContext)
                    .load(product.photoDark)
                    .into(ivProductBrowse)
                tvProductNameBrowse.text = product.name
                if (product.trending == true)
                    clTrendingBrowse.visibility = ConstraintLayout.VISIBLE
                else clTrendingBrowse.visibility = ConstraintLayout.GONE
                tvProductNameBrowse.isSelected = true
                tvProductPriceBrowse.text = StringBuilder().append("$").append(product.price)
                tvLikeCounterBrowse.text = StringBuilder().append(product.listUserLike?.size).append(" likes")
                tvCommentCounterBrowse.text = StringBuilder().append(product.commentCounter).append(" comments")
            }
        }
    }

}