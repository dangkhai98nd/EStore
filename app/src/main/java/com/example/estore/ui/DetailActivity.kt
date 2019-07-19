package com.example.estore.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val productDetail = Product()

        Glide.with(this)
            .load(productDetail.photoDark)
            .into(productPhotoDetail)

        tvProductNameDetail.text = productDetail.name
        tvProductPriceDetail.text = StringBuilder().append("$").append(productDetail.price)
        tvLikeCounterDetail.text =
            StringBuilder().append(productDetail.likeCounter).append(" likes")
        tvCommentCounterDetail.text =
            StringBuilder().append(productDetail.commentCounter).append(" comments")
        numberLikeDetail.text =
            StringBuilder().append(productDetail.likeCounter).append(" people like this")

        productDetail.trending = true

        if (productDetail.trending!!) {
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
            if (star5click) resetStar() else set5star()
        }

        star4.setOnClickListener {
            if (star4click) resetStar() else set4star()
        }

        star3.setOnClickListener {
            if (star3click) resetStar() else set3star()
        }

        star2.setOnClickListener {
            if (star2click) resetStar() else set2star()
        }

        star1.setOnClickListener {
            if (star1click) resetStar() else set1star()
        }

        buttonFavoriteDetail.setOnClickListener {
            favoriteClick = if(!favoriteClick){
                buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditem)
                true
            }else{
                buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditemdisabled)
                false
            }
        }

        buttonHeartDetail.setOnClickListener {
            heartClick = if(!heartClick){
                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemenabled)
                true
            }else{
                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemdisabled)
                false
            }
        }

        buttonAddCartDetail.setOnClickListener {
            if(!cartClick) {
                buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_cart_uncheck)
                imageCartCheck.visibility = View.VISIBLE
                productDetail.id?.let { it1 -> userEstore?.cartList?.add(it1) }
                cartClick = true
            }else{
                buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_login)
                imageCartCheck.visibility = View.INVISIBLE
                productDetail.id?.let { it1 -> userEstore?.cartList?.remove(it1) }
                cartClick = false
            }
        }
    }

    override fun onBackPressed() {
        userEstore?.let { firebaseFunction.updateUser(it) }
        super.onBackPressed()
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
}
