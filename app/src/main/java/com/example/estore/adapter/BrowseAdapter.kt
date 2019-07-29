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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.ui.DetailActivity
import kotlinx.android.extensions.LayoutContainer

class BrowseAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<BrowseAdapter.ItemViewHolder>() {

    private var products : List<Product> = listOf()

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
        private val buttonHeartBrowse = containerView.findViewById<ImageView>(R.id.buttonHeartBrowse)
        private val buttonFavoriteBrowse = containerView.findViewById<ImageView>(R.id.buttonFavoriteBrowse)
        private var favoriteClick = false
        private var heartClick = false


        fun bind(product: Product) {
            Glide.with(mContext)
                .load(product.photoDark)
                .thumbnail(Glide.with(mContext).load(R.drawable.load))
                .fitCenter()
                .into(ivProductBrowse)
            tvProductNameBrowse.text = product.name
            if (product.trending == true)
                clTrendingBrowse.visibility = ConstraintLayout.VISIBLE
            else clTrendingBrowse.visibility = ConstraintLayout.GONE
            tvProductNameBrowse.isSelected = true
            tvProductPriceBrowse.text = StringBuilder().append("$").append(product.price)
            tvLikeCounterBrowse.text = StringBuilder().append(product.listUserLike.size).append(" likes")
            tvCommentCounterBrowse.text = StringBuilder().append(product.commentCounter).append(" comments")
            cvItemBrowse.setOnClickListener {
                val intent = Intent(mContext, DetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("position", database.indexOf(product))
                mContext.startActivity(intent)
            }

            if (userEstore.value?.listFavorite?.contains(product.id) == true) {
                favoriteClick = true
                buttonFavoriteBrowse.setImageResource(R.drawable.ic_favoriteditem)
            } else {
                favoriteClick = false
                buttonFavoriteBrowse.setImageResource(R.drawable.ic_favoriteditemdisabled)
            }

            if (product.listUserLike.contains(userEstore.value?.id)) {
                heartClick = true
                buttonHeartBrowse.setImageResource(R.drawable.ic_heartitemenabled)
            } else {
                heartClick = false
                buttonHeartBrowse.setImageResource(R.drawable.ic_heartitemdisabled)
            }

            buttonFavoriteBrowse.setOnClickListener {
                val firebaseFunction = FirebaseFunction()
                favoriteClick = if (!favoriteClick) {
                    buttonFavoriteBrowse.setImageResource(R.drawable.ic_favoriteditem)
                    product.id?.let { it1 -> userEstore.value?.listFavorite?.add(it1) }
                    DatabaseEstore.updatauser(userEstore.value!!)
                    firebaseFunction.updateAny("User", userEstore.value!!.id!!, "listFavorite", userEstore.value!!.listFavorite)
                    true
                } else {
                    buttonFavoriteBrowse.setImageResource(R.drawable.ic_favoriteditemdisabled)
                    product.id?.let { it1 -> userEstore.value?.listFavorite?.remove(it1) }
                    DatabaseEstore.updatauser(userEstore.value!!)
                    firebaseFunction.updateAny("User", userEstore.value!!.id!!, "listFavorite", userEstore.value!!.listFavorite)
                    false
                }
            }

            buttonHeartBrowse.setOnClickListener {
                val firebaseFunction = FirebaseFunction()
                heartClick = if (!heartClick) {
                    buttonHeartBrowse.setImageResource(R.drawable.ic_heartitemenabled)
                    userEstore.value?.id?.let { it1 -> product.listUserLike.add(it1) }
                    tvLikeCounterBrowse.text = StringBuilder().append(product.listUserLike.size).append(" likes")
                    firebaseFunction.updateAny("Product", product.id!!, "listUserLike", product.listUserLike)
                    true
                } else {
                    buttonHeartBrowse.setImageResource(R.drawable.ic_heartitemdisabled)
                    userEstore.value?.id?.let { it1 -> product.listUserLike.remove(it1) }
                    tvLikeCounterBrowse.text = StringBuilder().append(product.listUserLike.size).append(" likes")
                    firebaseFunction.updateAny("Product", product.id!!, "listUserLike", product.listUserLike)
                    false
                }
            }
        }
    }

//    fun addAll(products : List<Product>) {
//        this.products = products
//        notifyDataSetChanged()
//    }

//    fun add(product: Product) {
//        this.products.add(product)
//        notifyDataSetChanged()
//    }
    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
}