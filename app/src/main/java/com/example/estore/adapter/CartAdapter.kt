package com.example.estore.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.estore.R
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.model.ProductCart
import kotlinx.android.extensions.LayoutContainer

class CartAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {
    //    private lateinit var databaseRef: DatabaseReference
    private var cartList: List<ProductCart> = listOf()
    private var productList : MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false))
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        productList.add(holder.bind(position) ?: return)
    }

    fun getSubtotal() : Int {
        val temp : List<Int> = cartList.zip(productList) {a,b-> a.quantity?.times(b.price ?: 0) ?: 0 }
        return temp.sumBy { it }
    }

    inner class ItemViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private val cvColorCart = containerView.findViewById<CardView>(R.id.cvColorCart)
        private val ivProductCart = containerView.findViewById<ImageView>(R.id.ivProductCart)
        private val tvProductNameCart = containerView.findViewById<TextView>(R.id.tvProductNameCart)
        private val tvPriceCart = containerView.findViewById<TextView>(R.id.tvPriceCart)
        private val edtQuantityCart = containerView.findViewById<EditText>(R.id.edtQuantityCart)
        private val ivMinusCart = containerView.findViewById<ImageView>(R.id.ivMinusCart)
        private val ivAddCart = containerView.findViewById<ImageView>(R.id.ivAddCart)
        private val tvColorCart = containerView.findViewById<TextView>(R.id.tvColorCart)

        fun bind(position: Int) : Product? {
            val productCart: ProductCart? = userEstore?.cartList?.get(position)
            var product : Product? = null
            database.forEach {
                if (it.id == productCart?.idProduct)
                {
                    product = it
                    return@forEach
                }
            }

            edtQuantityCart.setText(productCart?.quantity.toString())
            Log.e("cart","${productCart?.quantity}")
            if (productCart?.color == "dark") {
                cvColorCart.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorDark))
                tvColorCart.text = "Dark"
                Glide.with(mContext)
                    .load(product?.photoDark)
                    .into(ivProductCart)
            } else {
                cvColorCart.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite))
                tvColorCart.text = "Light"
                Glide.with(mContext)
                    .load(product?.photoLight)
                    .into(ivProductCart)
            }
//            listenerChange(productCart?.idProduct)
            ivMinusCart.setOnClickListener {
                productCart?.quantity = productCart?.quantity?.minus(1)
                edtQuantityCart.setText(productCart?.quantity.toString())
                if (productCart != null)
                    userEstore?.cartList?.set(position, productCart)
            }

            ivAddCart.setOnClickListener {
                productCart?.quantity = productCart?.quantity?.plus(1)
                edtQuantityCart.setText(productCart?.quantity.toString())
                if (productCart != null)
                    userEstore?.cartList?.set(position, productCart)
            }
            tvProductNameCart.text = product?.name
            tvPriceCart.text = "\$" + "${product?.price}"
            return product
        }
    }

    fun add(cartList: MutableList<ProductCart>?) {
        this.cartList = cartList ?: listOf()
        notifyDataSetChanged()
    }

//    fun listenerChange (id : String?) {
//        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
//        databaseRef.child("$id").addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                val product = p0.getValue(Product::class.java)
//                if (product != null) {
//                    Log.e("comment count", "${product.commentCounter}")
//                }
//            }
//
//        })
//    }
}