package com.example.estore.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.adapter.ListLikeAdapter
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.model.ProductCart
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
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
    private var cartAdded = false
    private var listLikeAdapter: ListLikeAdapter? = null
    private var listLikeSize = 0
    private var quantity = 0
    private var colorChoose = "dark"
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent
        val position = intent.getIntExtra("position", -1)
        var productDetail = database[position]

        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
        productDetail.id?.let {
            databaseRef.child(it).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    productDetail = p0.getValue(Product::class.java)!!
                    setUpAdapter(productDetail.listUserLike)
                    listLikeAdapter?.notifyDataSetChanged()
                    tvLikeCounterDetail.text =
                        StringBuilder().append(productDetail.listUserLike.size).append(" likes")
                    tvCommentCounterDetail.text =
                        StringBuilder().append(productDetail.commentCounter).append(" comments")
                    numberLikeDetail.text =
                        StringBuilder().append(productDetail.listUserLike.size).append(" people like this")
                }
            })
        }

        val behavior = BottomSheetBehavior.from(bottom_sheet)

        initToolbar()

        val index = userEstore?.cartList?.indexOfFirst { it.idProduct == productDetail.id }
        if (index != null) {
            if(index >= 0){
                buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_cart_checked)
                imageCartCheck.visibility = View.VISIBLE
                buttonAddCartDetail.text = StringBuilder().append("ADDED TO CART")
                darkButtonDetail.isEnabled = false
                lightButtonDetail.isEnabled = false

                ivAddCartDetail.isEnabled = false
                ivMinusCartDetail.isEnabled = false
                edtQuantityCartDetail.isEnabled = false

                cartAdded = true

                quantity = userEstore?.cartList?.get(index)?.quantity!!
                edtQuantityCartDetail.setText(quantity.toString())
                val color = userEstore?.cartList?.get(index)?.color
                if(color == "dark"){
                    darkButtonDetail.isChecked = true
                    lightButtonDetail.isChecked = false

                    colorChoose = "dark"

                    Glide.with(this)
                        .load(productDetail.photoDark)
                        .into(productPhotoDetail)
                    Glide.with(this)
                        .load(productDetail.photoDark)
                        .into(imageInSheet)
                }else{
                    darkButtonDetail.isChecked = false
                    lightButtonDetail.isChecked = true

                    Glide.with(this)
                        .load(productDetail.photoLight)
                        .into(productPhotoDetail)
                    Glide.with(this)
                        .load(productDetail.photoLight)
                        .into(imageInSheet)

                    colorChoose = "light"
                }
            }else{
                Glide.with(this)
                    .load(productDetail.photoDark)
                    .into(productPhotoDetail)
                Glide.with(this)
                    .load(productDetail.photoDark)
                    .into(imageInSheet)
                darkButtonDetail.isChecked = true
                lightButtonDetail.isChecked = false
                edtQuantityCartDetail.setText(quantity.toString())

                ivAddCartDetail.isEnabled = true
                ivMinusCartDetail.isEnabled = true
                edtQuantityCartDetail.isEnabled = true
            }
        }

        nameInSheet.text = productDetail.name
        priceInSheet.text = StringBuilder().append("$").append(productDetail.price)

        tvProductNameDetail.text = productDetail.name
        tvProductNameDetail.isSelected = true

        tvProductPriceDetail.text = StringBuilder().append("$").append(productDetail.price)

        listLikeSize = productDetail.listUserLike.size

        tvLikeCounterDetail.text =
            StringBuilder().append(listLikeSize).append(" likes")
        tvCommentCounterDetail.text =
            StringBuilder().append(productDetail.commentCounter).append(" comments")
        numberLikeDetail.text =
            StringBuilder().append(listLikeSize).append(" people like this")


        if(userEstore?.listFavorite?.contains(productDetail.id) == true){
            favoriteClick = true
            buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditem)
        }else {
            favoriteClick = false
            buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditemdisabled)
        }

        if(productDetail.listUserLike.contains(userEstore?.id)){
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
            productDetail.rating == 0 ->{
                resetStar()
            }
        }
        star5.setOnClickListener {
            if (star5click) {
                productDetail.rating = 0
                resetStar()
            } else{
                set5star()
                productDetail.rating = 5
            }
        }

        star4.setOnClickListener {
            if (star4click) {
                productDetail.rating = 0
                resetStar()
            } else {
                set4star()
                productDetail.rating = 4
            }
        }

        star3.setOnClickListener {
            if (star3click) {
                productDetail.rating = 0
                resetStar()
            } else {
                set3star()
                productDetail.rating = 3
            }
        }

        star2.setOnClickListener {
            if (star2click) {
                productDetail.rating = 0
                resetStar()
            } else {
                set2star()
                productDetail.rating = 2
            }
        }

        star1.setOnClickListener {
            if (star1click) {
                productDetail.rating = 0
                resetStar()
            } else {
                set1star()
                productDetail.rating = 1
            }
        }

        darkButtonDetail.setOnClickListener {
            lightButtonDetail.isChecked = false
            colorChoose = "dark"
            Glide.with(this)
                .load(productDetail.photoDark)
                .into(productPhotoDetail)

            Glide.with(this)
                .load(productDetail.photoDark)
                .into(imageInSheet)
        }
        lightButtonDetail.setOnClickListener {
            darkButtonDetail.isChecked = false
            colorChoose = "light"
            Glide.with(this)
                .load(productDetail.photoLight)
                .into(productPhotoDetail)

            Glide.with(this)
                .load(productDetail.photoLight)
                .into(imageInSheet)
        }

        buttonFavoriteDetail.setOnClickListener {
            favoriteClick = if(!favoriteClick){
                buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditem)
                productDetail.id?.let { it1 -> userEstore?.listFavorite?.add(it1) }
                firebaseFunction.updateAny("User", userEstore!!.id!!, "listFavorite", userEstore!!.listFavorite)
                true
            }else{
                buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditemdisabled)
                productDetail.id?.let { it1 -> userEstore?.listFavorite?.remove(it1) }
                firebaseFunction.updateAny("User", userEstore!!.id!!, "listFavorite", userEstore!!.listFavorite)
                false
            }
        }

        buttonHeartDetail.setOnClickListener {
            heartClick = if(!heartClick){
                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemenabled)
                userEstore?.id?.let { it1 -> productDetail.listUserLike.add(it1) }
                firebaseFunction.updateAny("Product", productDetail.id!!, "listUserLike", productDetail.listUserLike)
                listLikeSize++
                listLikeAdapter?.notifyDataSetChanged()
                numberLikeDetail.text = StringBuilder().append(listLikeSize).append(" people like this")
                tvLikeCounterDetail.text = StringBuilder().append(listLikeSize).append(" likes")
                true
            }else{
                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemdisabled)
                userEstore?.id?.let { it1 -> productDetail.listUserLike.remove(it1) }
                firebaseFunction.updateAny("Product", productDetail.id!!, "listUserLike", productDetail.listUserLike)
                listLikeSize--
                listLikeAdapter?.notifyDataSetChanged()
                numberLikeDetail.text = StringBuilder().append(listLikeSize).append(" people like this")
                tvLikeCounterDetail.text = StringBuilder().append(listLikeSize).append(" likes")
                false
            }
        }

        buttonAddCartDetail.setOnClickListener {
            if(!cartAdded) {
                if(quantity > 0){
                    if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_cart_checked)
                    imageCartCheck.visibility = View.VISIBLE
                    buttonAddCartDetail.text = StringBuilder().append("ADDED TO CART")
                    userEstore?.cartList?.add(ProductCart(productDetail.id, quantity, colorChoose))
                    darkButtonDetail.isEnabled = false
                    lightButtonDetail.isEnabled = false

                    ivAddCartDetail.isEnabled = false
                    ivMinusCartDetail.isEnabled = false
                    edtQuantityCartDetail.isEnabled = false

                    cartAdded = true
                    Handler().postDelayed({
                        firebaseFunction.updateAny("Product", productDetail.id!! , "rating", productDetail.rating.toString())
                        firebaseFunction.updateAny("User", userEstore?.id!!, "cartList", userEstore?.cartList!!)
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    },4000)
                }else{
                    Toast.makeText(this, "Number must be larger than 0", Toast.LENGTH_SHORT).show()
                }

            }else{
                buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_login)
                imageCartCheck.visibility = View.INVISIBLE
                buttonAddCartDetail.text = StringBuilder().append("ADD TO CART")
                val i = userEstore?.cartList?.indexOfFirst { it.idProduct == productDetail.id }
                userEstore?.cartList?.removeAt(i!!)
                firebaseFunction.updateAny("User", userEstore?.id!!, "cartList", userEstore?.cartList!!)

                darkButtonDetail.isEnabled = true
                lightButtonDetail.isEnabled = true

                ivAddCartDetail.isEnabled = true
                ivMinusCartDetail.isEnabled = true
                edtQuantityCartDetail.isEnabled = true

                cartAdded = false
            }
        }



        ivMinusCartDetail.setOnClickListener {
            if(quantity > 0){
                quantity -= 1
                edtQuantityCartDetail.setText(quantity.toString())
            }
        }
        ivAddCartDetail.setOnClickListener {
            quantity += 1
            edtQuantityCartDetail.setText(quantity.toString())
        }


        setUpAdapter(productDetail.listUserLike)
    }

    private fun initToolbar() {
        ic_searchDetail.setOnClickListener {
            groupNormalDetail.visibility = View.GONE
            groupSearchDetail.visibility = View.VISIBLE
        }
        ic_closeDetail.setOnClickListener {
            groupSearchDetail.visibility = View.GONE
            groupNormalDetail.visibility = View.VISIBLE
            edtSearchDetail.text?.clear()
        }
        ic_backDetail.setOnClickListener {
            onBackPressed()
        }
        tvSearchDetail.setOnClickListener {
            if (edtSearchDetail.text.toString() != "") {
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("keyword", edtSearchDetail.text.toString())
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        finish()
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

    private fun setUpAdapter(listLike: List<String>){
        listLikeAdapter = ListLikeAdapter(this, listLike)
        rvListLike.adapter = listLikeAdapter
        rvListLike.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }
}
