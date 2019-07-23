package com.example.estore.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.R
import com.example.estore.adapter.CartAdapter
import com.example.estore.model.DatabaseEstore
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : Fragment() {
    private var cartAdapter : CartAdapter? = null
    private var subtotal : Int = 0
    private var root : View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_cart,container,false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupAdapter(view)
        Handler().postDelayed({
            cartAdapter?.add(DatabaseEstore.userEstore?.cartList)
            Handler().postDelayed({
                subtotal = cartAdapter?.getSubtotal() ?: 0
                root?.tvSubtotalCart?.text = 1f.times(subtotal).toString()
                root?.tvTaxesCart?.text = 0.1f.times(subtotal).toString()
                root?.tvTotalCart?.text = 1.1f.times(subtotal).toString()
            },2000)
        },2000)

    }

    private fun setupAdapter(view : View) {
        cartAdapter = CartAdapter(view.context)
        rvCart.itemAnimator = DefaultItemAnimator()
        rvCart.adapter = cartAdapter
        rvCart.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)

    }
}