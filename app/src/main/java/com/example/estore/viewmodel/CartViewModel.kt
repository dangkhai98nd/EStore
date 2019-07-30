package com.example.estore.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.estore.R
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.Product
import com.example.estore.model.ProductCart
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.updateUser
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.User
import com.example.estore.ui.DetailActivity

class CartViewModel(
    private var mContext: Context? = null,
    var cart: ProductCart? = null,
    var product: Product? = null,
    private var position: Int? = null
) : ViewModel() {
    var quantity: ObservableField<String> = ObservableField()
    var textColor: String? = null
    var colorBackground: Int? = null
    var url: String? = null
    var subtotal: ObservableField<Float> = ObservableField(0f)

    init {
        quantity.set(cart?.quantity.toString())
        if (cart?.color == "dark") {
            colorBackground = mContext?.let { ContextCompat.getColor(it, R.color.colorDark) }
            textColor = "Dark"
            url = product?.photoDark
        } else {
            colorBackground = mContext?.let { ContextCompat.getColor(it, R.color.colorWhite) }
            textColor = "White"
            url = product?.photoLight
        }
    }

    fun increase() {
        cart?.quantity = cart?.quantity?.plus(1)
        quantity.set(cart?.quantity.toString())
        userEstore.value?.cartList?.set(position ?: return, cart ?: return)
        updateUser(userEstore.value)
    }

    fun decrease() {
        if (1 < cart?.quantity ?: return) {
            cart?.quantity = cart?.quantity?.minus(1)
            quantity.set(cart?.quantity.toString())
            userEstore.value?.cartList?.set(position ?: return, cart ?: return)
            updateUser(userEstore.value)
        }
    }

    fun detail() {
        val intent = Intent(mContext, DetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("position", database.indexOf(product))
        mContext?.startActivity(intent)
    }

    fun convertInttoStrPrice(price : Number) : String
    {
        return "\$" + "${price}"
    }
}