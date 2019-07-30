package com.example.estore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.databinding.ItemCartBinding
import com.example.estore.model.Product
import com.example.estore.model.ProductCart
import com.example.estore.viewmodel.CartViewModel

class CartAdapter(
    private val mContext: Context
) : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {
    private var cartList: List<ProductCart> = listOf()
    private var productList : List<Product> = listOf()
    private var firebaseFunction : FirebaseFunction = FirebaseFunction()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_cart, parent, false))
    }

    override fun getItemCount(): Int = cartList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }    inner class ItemViewHolder(
        private val itemCartBinding : ItemCartBinding


    ) : RecyclerView.ViewHolder(itemCartBinding.root) {
//        private val cvColorCart = containerView.findViewById<CardView>(R.id.cvColorCart)
//        private val ivProductCart = containerView.findViewById<ImageView>(R.id.ivProductCart)
//        private val tvProductNameCart = containerView.findViewById<TextView>(R.id.tvProductNameCart)
//        private val tvPriceCart = containerView.findViewById<TextView>(R.id.tvPriceCart)
//        private val edtQuantityCart = containerView.findViewById<EditText>(R.id.edtQuantityCart)
//        private val ivMinusCart = containerView.findViewById<ImageView>(R.id.ivMinusCart)
//        private val ivAddCart = containerView.findViewById<ImageView>(R.id.ivAddCart)
//        private val tvColorCart = containerView.findViewById<TextView>(R.id.tvColorCart)
//        private val cvCart = containerView.findViewById<CardView>(R.id.cvCart)

        fun bind(position: Int) {
            itemCartBinding.cartViewModel = CartViewModel(mContext,cartList[position],productList[position],position)
//            database.forEach {
//                if (it.id == productCart?.idProduct)
//                {
//                    product = it
//                    return@forEach
//                }
//            }

//            edtQuantityCart.setText(productCart?.quantity.toString())
//            if (productCart?.color == "dark") {
//                cvColorCart.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorDark))
//                tvColorCart.text = StringBuilder().append("Dark")
//                Glide.with(mContext)
//                    .load(product.photoDark)
//                    .thumbnail(Glide.with(mContext).load(R.drawable.load))
//                    .fitCenter()
//                    .into(ivProductCart)
//            } else {
//                cvColorCart.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite))
//                tvColorCart.text = StringBuilder().append("Light")
//                Glide.with(mContext)
//                    .load(product.photoLight)
//                    .thumbnail(Glide.with(mContext).load(R.drawable.load))
//                    .fitCenter()
//                    .into(ivProductCart)
//            }
////            listenerChange(productCart?.idProduct)
//            ivMinusCart.setOnClickListener {
//                if (1 < productCart?.quantity ?: 0)
//                productCart?.quantity = productCart?.quantity?.minus(1)
//                edtQuantityCart.setText(productCart?.quantity.toString())
//                if (productCart != null) {
//                    userEstore.value?.cartList?.set(position, productCart)
//                    updateUser(userEstore.value!!)
//                    firebaseFunction.updateAny("User", userEstore.value?.id!!,"cartList", userEstore.value?.cartList ?: return@setOnClickListener)
//                }
//
//            }
//
//            ivAddCart.setOnClickListener {
//                productCart?.quantity = productCart?.quantity?.plus(1)
//                edtQuantityCart.setText(productCart?.quantity.toString())
//                if (productCart != null) {
//                    userEstore.value?.cartList?.set(position, productCart)
//                    updateUser(userEstore.value!!)
//                    firebaseFunction.updateAny("User", userEstore.value?.id!!,"cartList", userEstore.value?.cartList ?: return@setOnClickListener)
//                }
//            }
//            tvProductNameCart.text = product.name
//            tvPriceCart.text = StringBuilder().append("$").append(product.price)
//            cvCart.setOnClickListener {
//                val intent = Intent(mContext, DetailActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                intent.putExtra("position", database.indexOf(product))
//                mContext.startActivity(intent)
//            }
        }
    }

    fun setData(cartList: List<ProductCart>? , productList : List<Product>) {
        this.cartList = cartList ?: emptyList()
        this.productList = productList
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