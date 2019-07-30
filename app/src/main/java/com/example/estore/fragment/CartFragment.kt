package com.example.estore.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.adapter.CartAdapter
import com.example.estore.databinding.FragmentCartBinding
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : Fragment() {
    private var cartAdapter: CartAdapter? = null
    private var subtotal: Float = 0f
    private var firebaseFunction : FirebaseFunction = FirebaseFunction()
    private var productList: MutableList<Product> = mutableListOf()
    private var fragmentCartBinding : FragmentCartBinding? = null
    private var cartViewModel : CartViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCartBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false)
        cartViewModel = CartViewModel()
        fragmentCartBinding?.cartViewModel = cartViewModel
        return fragmentCartBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupAdapter(view)

        userEstore.observe(this@CartFragment, Observer {user ->
            subtotal = 0f
            productList = mutableListOf()
            user?.cartList?.map { cart ->
                database.forEach {
                    if (it.id == cart.idProduct)
                    {
                        productList.add(it)
                        subtotal += it.price?.times(cart.quantity ?: 0) ?: 0

                    }
                }
            }
            firebaseFunction.updateAny("User",user.id ?: return@Observer,"cartList",user.cartList)
            cartViewModel?.subtotal?.set(subtotal)
            cartAdapter?.setData(user?.cartList,productList)
        })

        userEstore.value?.cartList?.map { cart ->
            database.forEach {
                if (it.id == cart.idProduct)
                {
                    productList.add(it)
                    subtotal += it.price?.times(cart.quantity ?: 0) ?: 0

                }
            }
        }
        cartViewModel?.subtotal?.set(subtotal)
        cartAdapter?.setData(userEstore.value?.cartList,productList)

    }

    private fun setupAdapter(view: View) {
        cartAdapter = CartAdapter(view.context)
        rvCart.itemAnimator = DefaultItemAnimator()
        rvCart.adapter = cartAdapter
        rvCart.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

    }
}