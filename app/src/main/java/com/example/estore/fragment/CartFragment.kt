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
import com.example.estore.model.DatabaseEstore
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {
    private var cartAdapter : CartAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter(view)
        Handler().postDelayed({
            cartAdapter?.add(DatabaseEstore.userEstore?.cartList)
            Handler().postDelayed({
                val subtotal = cartAdapter?.getSubtotal()
                tvSubtotalCart.text = subtotal.toString()
                tvTaxesCart.text = 0.1f.times(subtotal ?: 0).toString()
                tvTotalCart.text = 1.1f.times(subtotal ?: 0).toString()
            },1000)
        },2000)

    }

    private fun setupAdapter(view : View) {
        cartAdapter = CartAdapter(view.context)
        rvCart.itemAnimator = DefaultItemAnimator()
        rvCart.adapter = cartAdapter
        rvCart.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL,false)

    }
}