package com.example.estore.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.R
import com.example.estore.adapter.CartAdapter
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : Fragment() {
    private var cartAdapter: CartAdapter? = null
    private var subtotal: Int = 0
    private var root: View? = null
    private var productList: MutableList<Product> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_cart, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupAdapter(view)



        userEstore?.cartList?.map { cart ->
            database.forEach {
                if (it.id == cart.idProduct)
                {
                    productList.add(it)
                    subtotal += it.price?.times(cart.quantity ?: return) ?: 0
                }
            }
        }
        cartAdapter?.setData(userEstore?.cartList,productList)
        root?.tvSubtotalCart?.text = """${"$"}${1f.times(subtotal)}"""
        root?.tvTaxesCart?.text = "\$" + 0.1f.times(subtotal).toString()
        root?.tvTotalCart?.text = "\$" + 1.1f.times(subtotal).toString()


    }

    private fun setupAdapter(view: View) {
        cartAdapter = CartAdapter(view.context)
        rvCart.itemAnimator = DefaultItemAnimator()
        rvCart.adapter = cartAdapter
        rvCart.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

    }
}