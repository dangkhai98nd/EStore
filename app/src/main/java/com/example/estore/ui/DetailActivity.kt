package com.example.estore.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.adapter.ListLikeAdapter
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.model.ProductCart
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var firebaseFunction = FirebaseFunction()
    private var star1click = false
    private var star2click = false
    private var star3click = false
    private var star4click = false
    private var star5click = false
    private var favoriteClick = false
    private var heartClick = false
    private var cartClick = false
    private var productToPush: Product? = null
    private var listLikeAdapter: ListLikeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val behavior = BottomSheetBehavior.from(bottom_sheet)

        intent
        val position = intent.getIntExtra("position", -1)

        val productDetail = database[position]

        nameInSheet.text = productDetail.name
        priceInSheet.text = StringBuilder().append("$").append(productDetail.price)
        Glide.with(this)
            .load(productDetail.photoDark)
            .into(imageInSheet)

        Glide.with(this)
            .load(productDetail.photoDark)
            .into(productPhotoDetail)

        tvProductNameDetail.text = productDetail.name
        tvProductPriceDetail.text = StringBuilder().append("$").append(productDetail.price)
        tvLikeCounterDetail.text =
            StringBuilder().append(productDetail.listUserLike?.size).append(" likes")
        tvCommentCounterDetail.text =
            StringBuilder().append(productDetail.commentCounter).append(" comments")
        numberLikeDetail.text =
            StringBuilder().append(productDetail.listUserLike?.size).append(" people like this")


        if(userEstore?.listFavorite?.contains(productDetail.id) == true){
            favoriteClick = true
            buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditem)
        }else {
            favoriteClick = false
            buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditemdisabled)
        }

        if(productDetail.listUserLike?.contains(userEstore?.id) == true){
            heartClick = true
            buttonHeartDetail.setImageResource(R.drawable.ic_heartitemenabled)
        }else{
            heartClick = false
            buttonHeartDetail.setImageResource(R.drawable.ic_heartitemdisabled)
        }


        if (productDetail.trending == true) {
            tvTrendingDetail.visibility = View.VISIBLE
        } else {
            tvTrendingDetail.visibility = View.INVISIBLE
        }

        when {
            productDetail.rating == 5 -> {
                set5star()
            }
            productDetail.rating == 4 -> {
                set4star()
            }
            productDetail.rating == 3 -> {
                set3star()
            }
            productDetail.rating == 2 -> {
                set2star()
            }
            productDetail.rating == 1 -> {
                set1star()
            }
        }
        star5.setOnClickListener {
            if (star5click) resetStar() else{
                set5star()
                productDetail.rating = 5
            }
        }

        star4.setOnClickListener {
            if (star4click) resetStar() else {
                set4star()
                productDetail.rating = 4
            }
        }

        star3.setOnClickListener {
            if (star3click) resetStar() else {
                set3star()
                productDetail.rating = 3
            }
        }

        star2.setOnClickListener {
            if (star2click) resetStar() else {
                set2star()
                productDetail.rating = 2
            }
        }

        star1.setOnClickListener {
            if (star1click) resetStar() else {
                set1star()
                productDetail.rating = 1
            }
        }

        buttonFavoriteDetail.setOnClickListener {
            favoriteClick = if(!favoriteClick){
                buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditem)
                productDetail.id?.let { it1 -> userEstore?.listFavorite?.add(it1) }
                true
            }else{
                buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditemdisabled)
                productDetail.id?.let { it1 -> userEstore?.listFavorite?.remove(it1) }
                false
            }
        }

        buttonHeartDetail.setOnClickListener {
            heartClick = if(!heartClick){
                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemenabled)
                userEstore?.id?.let { it1 -> productDetail.listUserLike?.add(it1) }
                numberLikeDetail.text =
                    StringBuilder().append(productDetail.listUserLike?.size).append(" people like this")
                tvLikeCounterDetail.text =
                    StringBuilder().append(productDetail.listUserLike?.size).append(" likes")
                true
            }else{
                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemdisabled)
                userEstore?.id?.let { it1 -> productDetail.listUserLike?.remove(it1) }
                numberLikeDetail.text =
                    StringBuilder().append(productDetail.listUserLike?.size).append(" people like this")
                tvLikeCounterDetail.text =
                    StringBuilder().append(productDetail.listUserLike?.size).append(" likes")
                false
            }
        }

        buttonAddCartDetail.setOnClickListener {
            if(!cartClick) {
                if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_cart_checked)
                imageCartCheck.visibility = View.VISIBLE
                buttonAddCartDetail.text = StringBuilder().append("ADDED TO CART")
                productDetail.id?.let { it1 -> userEstore?.cartList?.add(ProductCart(it1)) }
                cartClick = true
                Handler().postDelayed({
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                },4000)
            }else{
                buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_login)
                imageCartCheck.visibility = View.INVISIBLE
                buttonAddCartDetail.text = StringBuilder().append("ADD TO CART")
                productDetail.id?.let { it1 -> userEstore?.cartList?.remove(ProductCart(it1)) }
                cartClick = false
            }
        }

        productDetail.listUserLike?.let { setUpAdapter(it) }



        productToPush = productDetail
    }

    override fun onBackPressed() {
//        userEstore?.let { firebaseFunction.updateUser(it) }
//        productToPush?.let { firebaseFunction.updateProduct(it) }
        finish()
        super.onBackPressed()
    }

    override fun onPause() {
        userEstore?.let { firebaseFunction.updateUser(it) }
//        productToPush?.let { firebaseFunction.updateProduct(it) }
        super.onPause()
    }

    private fun set5star() {
        star1.setImageResource(R.drawable.ic_star_red_24dp)
        star2.setImageResource(R.drawable.ic_star_red_24dp)
        star3.setImageResource(R.drawable.ic_star_red_24dp)
        star4.setImageResource(R.drawable.ic_star_red_24dp)
        star5.setImageResource(R.drawable.ic_star_red_24dp)
        star1click = false
        star2click = false
        star3click = false
        star4click = false
        star5click = true
    }

    private fun set4star() {
        star1.setImageResource(R.drawable.ic_star_red_24dp)
        star2.setImageResource(R.drawable.ic_star_red_24dp)
        star3.setImageResource(R.drawable.ic_star_red_24dp)
        star4.setImageResource(R.drawable.ic_star_red_24dp)
        star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        star1click = false
        star2click = false
        star3click = false
        star4click = true
        star5click = false
    }

    private fun set3star() {
        star1.setImageResource(R.drawable.ic_star_red_24dp)
        star2.setImageResource(R.drawable.ic_star_red_24dp)
        star3.setImageResource(R.drawable.ic_star_red_24dp)
        star4.setImageResource(R.drawable.ic_star_border_red_24dp)
        star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        star1click = false
        star2click = false
        star3click = true
        star4click = false
        star5click = false
    }

    private fun set2star() {
        star1.setImageResource(R.drawable.ic_star_red_24dp)
        star2.setImageResource(R.drawable.ic_star_red_24dp)
        star3.setImageResource(R.drawable.ic_star_border_red_24dp)
        star4.setImageResource(R.drawable.ic_star_border_red_24dp)
        star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        star1click = false
        star2click = true
        star3click = false
        star4click = false
        star5click = false
    }

    private fun set1star() {
        star1.setImageResource(R.drawable.ic_star_red_24dp)
        star2.setImageResource(R.drawable.ic_star_border_red_24dp)
        star3.setImageResource(R.drawable.ic_star_border_red_24dp)
        star4.setImageResource(R.drawable.ic_star_border_red_24dp)
        star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        star1click = true
        star2click = false
        star3click = false
        star4click = false
        star5click = false
    }

    private fun resetStar() {
        star1.setImageResource(R.drawable.ic_star_border_red_24dp)
        star2.setImageResource(R.drawable.ic_star_border_red_24dp)
        star3.setImageResource(R.drawable.ic_star_border_red_24dp)
        star4.setImageResource(R.drawable.ic_star_border_red_24dp)
        star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        star1click = false
        star2click = false
        star3click = false
        star4click = false
        star5click = false
    }

    private fun setUpAdapter(listLike: List<String>){
        listLikeAdapter = ListLikeAdapter(this, listLike)
        rvListLike.adapter = listLikeAdapter
        rvListLike.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }
}
