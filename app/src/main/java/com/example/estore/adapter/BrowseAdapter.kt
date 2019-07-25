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
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.ui.DetailActivity
import com.google.firebase.database.*
import kotlinx.android.extensions.LayoutContainer

class BrowseAdapter (
    private val mContext : Context
) : RecyclerView.Adapter<BrowseAdapter.ItemViewHolder>() {

    private var products : List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_browse,parent,false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(products[position], position)
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
        private lateinit var databaseRef : DatabaseReference


        fun bind(product : Product, position: Int) {
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
            cvItemBrowse.setOnClickListener {
//                val firebaseFunction = FirebaseFunction()
//                firebaseFunction.updateProduct(product)
//                userEstore?.let { it1 -> firebaseFunction.updateUser(it1) }
                val intent = Intent(mContext,DetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("position", position)
                mContext.startActivity(intent)
            }

            databaseRef = FirebaseDatabase.getInstance().getReference("Product")

            product.id?.let {
                databaseRef.child(it).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val productGet = p0.getValue(Product::class.java)
                        if(productGet != null){
                            if(userEstore?.listFavorite?.contains(productGet.id) == true){
                                favoriteClick = true
                                buttonFavoriteBrowse.setImageResource(R.drawable.ic_favoriteditem)
                            }else {
                                favoriteClick = false
                                buttonFavoriteBrowse.setImageResource(R.drawable.ic_favoriteditemdisabled)
                            }

                            if(productGet.listUserLike?.contains(userEstore?.id) == true){
                                heartClick = true
                                buttonHeartBrowse.setImageResource(R.drawable.ic_heartitemenabled)
                            }else{
                                heartClick = false
                                buttonHeartBrowse.setImageResource(R.drawable.ic_heartitemdisabled)
                            }

                            buttonFavoriteBrowse.setOnClickListener {
                                favoriteClick = if(!favoriteClick){
                                    buttonFavoriteBrowse.setImageResource(R.drawable.ic_favoriteditem)
                                    productGet.id?.let { it1 -> userEstore?.listFavorite?.add(it1) }
                                    true
                                }else{
                                    buttonFavoriteBrowse.setImageResource(R.drawable.ic_favoriteditemdisabled)
                                    productGet.id?.let { it1 -> userEstore?.listFavorite?.remove(it1) }
                                    false
                                }
                            }

                            buttonHeartBrowse.setOnClickListener {
                                heartClick = if(!heartClick){
                                    buttonHeartBrowse.setImageResource(R.drawable.ic_heartitemenabled)
                                    tvLikeCounterBrowse.text = StringBuilder().append(productGet.listUserLike?.size?.plus(1)).append(" likes")
                                    userEstore?.id?.let { it1 -> productGet.listUserLike?.add(it1) }
                                    userEstore?.id?.let { it1 -> product.listUserLike?.add(it1) }
//                                    userEstore?.id?.let { it1 -> database[position].listUserLike?.add(it1) }
                                    true
                                }else{
                                    buttonHeartBrowse.setImageResource(R.drawable.ic_heartitemdisabled)
                                    tvLikeCounterBrowse.text = StringBuilder().append(productGet.listUserLike?.size?.minus(1)).append(" likes")
                                    userEstore?.id?.let { it1 -> productGet.listUserLike?.remove(it1) }
                                    userEstore?.id?.let { it1 -> product.listUserLike?.remove(it1) }
//                                    userEstore?.id?.let { it1 -> database[position].listUserLike?.remove(it1) }
                                    false
                                }
                            }
                        }

                    }
                })
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