package com.example.estore.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.adapter.ListLikeAdapter
import com.example.estore.databinding.ActivityDetailBinding
import com.example.estore.model.DatabaseEstore.Companion.database
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var firebaseFunction = FirebaseFunction()
    private var listLikeAdapter: ListLikeAdapter? = null
    private var listLikeSize = 0
    private lateinit var databaseRef: DatabaseReference
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        intent
        val position = intent.getIntExtra("position", -1)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        viewModel.photoLink.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .into(binding.productPhotoDetail)
            Glide.with(this)
                .load(it)
                .into(binding.imageCartInSheet)
        })
        viewModel.rating.observe(this, Observer {
            when (it) {
                5 -> {
                    set5star()
                }
                4 -> {
                    set4star()
                }
                3 -> {
                    set3star()
                }
                2 -> {
                    set2star()
                }
                1 -> {
                    set1star()
                }
                0 -> {
                    resetStar()
                }
            }
        })
        viewModel.heartClick.observe(this, Observer {
            if (it) {
                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemenabled)
            } else buttonHeartDetail.setImageResource(R.drawable.ic_heartitemdisabled)
        })
        viewModel.trending.observe(this, Observer {
            if(it){
                binding.clTrendingDetail.visibility = View.VISIBLE
            }else binding.clTrendingDetail.visibility = View.GONE
        })
        viewModel.favoriteClick.observe(this, Observer {
            if(it){
                binding.buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditem)
            }else buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditemdisabled)
        })


        viewModel.productDetail = database[position]
        viewModel.getImage()
        viewModel.getRating()
        viewModel.getHeart()
        viewModel.getTrending()
        viewModel.getFavoriteButton()
        binding.darkButtonDetail.isChecked = true
        binding.lightButtonDetail.isChecked = false



        val behavior = BottomSheetBehavior.from(bottom_sheet)

        initToolbar()

        binding.apply {
            nameInSheet.text = viewModel.productDetail.name
            priceInSheet.text = StringBuilder().append("$").append(viewModel.productDetail.price)
            tvProductNameDetail.text = viewModel.productDetail.name
            tvProductNameDetail.isSelected = true
            tvProductPriceDetail.text =
                StringBuilder().append("$").append(viewModel.productDetail.price)
        }

        binding.darkButtonDetail.setOnClickListener {
            binding.lightButtonDetail.isChecked = false
            viewModel.colorChoose = "dark"
            viewModel.getImage()
        }

        binding.lightButtonDetail.setOnClickListener {
            binding.darkButtonDetail.isChecked = false
            viewModel.colorChoose = "light"
            viewModel.getImage()
        }

        star5.setOnClickListener {
            if (viewModel.star5click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 5
            }
            viewModel.getRating()
        }

        star4.setOnClickListener {
            if (viewModel.star4click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 4
            }
            viewModel.getRating()
        }

        star3.setOnClickListener {
            if (viewModel.star3click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 3
            }
            viewModel.getRating()
        }

        star2.setOnClickListener {
            if (viewModel.star2click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 2
            }
            viewModel.getRating()
        }

        star1.setOnClickListener {
            if (viewModel.star1click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 1
            }
            viewModel.getRating()
        }
//        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
//        productDetail.id?.let {
//            databaseRef.child(it).addValueEventListener(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    productDetail = p0.getValue(Product::class.java)!!
//                    setUpAdapter(productDetail.listUserLike)
//                    listLikeAdapter?.notifyDataSetChanged()
//                    tvLikeCounterDetail.text =
//                        StringBuilder().append(productDetail.listUserLike.size).append(" likes")
//                    tvCommentCounterDetail.text =
//                        StringBuilder().append(productDetail.commentCounter).append(" comments")
//                    numberLikeDetail.text =
//                        StringBuilder().append(productDetail.listUserLike.size).append(" people like this")
//                }
//            })
//        }
//        val index = userEstore.value?.cartList?.indexOfFirst { it.idProduct == productDetail.id }
//        if (index != null) {
//            if(index >= 0){
//                buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_cart_checked)
//                imageCartCheck.visibility = View.VISIBLE
//                buttonAddCartDetail.text = StringBuilder().append("ADDED TO CART")
//                darkButtonDetail.isEnabled = false
//                lightButtonDetail.isEnabled = false
//
//                ivAddCartDetail.isEnabled = false
//                ivMinusCartDetail.isEnabled = false
//                edtQuantityCartDetail.isEnabled = false
//
//                cartAdded = true
//
//                quantity = userEstore.value?.cartList?.get(index)?.quantity!!
//                edtQuantityCartDetail.setText(quantity.toString())
//                val color = userEstore.value?.cartList?.get(index)?.color
//                if(color == "dark"){
//                    darkButtonDetail.isChecked = true
//                    lightButtonDetail.isChecked = false
//
//                    colorChoose = "dark"
//
//                    Glide.with(this)
//                        .load(productDetail.photoDark)
//                        .thumbnail(Glide.with(this).load(R.drawable.load))
//                        .into(productPhotoDetail)
//                    Glide.with(this)
//                        .load(productDetail.photoDark)
//                        .thumbnail(Glide.with(this).load(R.drawable.load))
//                        .into(imageInSheet)
//                }else{
//                    darkButtonDetail.isChecked = false
//                    lightButtonDetail.isChecked = true
//
//                    Glide.with(this)
//                        .load(productDetail.photoLight)
//                        .thumbnail(Glide.with(this).load(R.drawable.load))
//                        .into(productPhotoDetail)
//                    Glide.with(this)
//                        .load(productDetail.photoLight)
//                        .thumbnail(Glide.with(this).load(R.drawable.load))
//                        .into(imageInSheet)
//
//                    colorChoose = "light"
//                }
//            }else{
//                Glide.with(this)
//                    .load(productDetail.photoDark)
//                    .thumbnail(Glide.with(this).load(R.drawable.load))
//                    .into(productPhotoDetail)
//                Glide.with(this)
//                    .load(productDetail.photoDark)
//                    .thumbnail(Glide.with(this).load(R.drawable.load))
//                    .into(imageInSheet)
//                darkButtonDetail.isChecked = true
//                lightButtonDetail.isChecked = false
//                edtQuantityCartDetail.setText(quantity.toString())
//
//                ivAddCartDetail.isEnabled = true
//                ivMinusCartDetail.isEnabled = true
//                edtQuantityCartDetail.isEnabled = true
//            }
//        }
//        listLikeSize = productDetail.listUserLike.size
//
//

//
//
//


//        buttonFavoriteDetail.setOnClickListener {
//            viewModel.favoriteClick = if(!viewModel.favoriteClick){
//                buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditem)
//                productDetail.id?.let { it1 -> userEstore.value?.listFavorite?.add(it1) }
//                updateUser(userEstore.value!!)
//                firebaseFunction.updateAny("User", userEstore.value!!.id!!, "listFavorite", userEstore.value!!.listFavorite)
//                true
//            }else{
//                buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditemdisabled)
//                productDetail.id?.let { it1 -> userEstore.value?.listFavorite?.remove(it1) }
//                updateUser(userEstore.value!!)
//                firebaseFunction.updateAny("User", userEstore.value!!.id!!, "listFavorite", userEstore.value!!.listFavorite)
//                false
//            }
//        }

//        buttonHeartDetail.setOnClickListener {
//            viewModel.heartClick = if(!viewModel.heartClick){
//                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemenabled)
//                userEstore.value?.id?.let { it1 -> productDetail.listUserLike.add(it1) }
//                firebaseFunction.updateAny("Product", productDetail.id!!, "listUserLike", productDetail.listUserLike)
//                listLikeSize++
//                listLikeAdapter?.notifyDataSetChanged()
//                numberLikeDetail.text = StringBuilder().append(listLikeSize).append(" people like this")
//                tvLikeCounterDetail.text = StringBuilder().append(listLikeSize).append(" likes")
//                true
//            }else{
//                buttonHeartDetail.setImageResource(R.drawable.ic_heartitemdisabled)
//                userEstore.value?.id?.let { it1 -> productDetail.listUserLike.remove(it1) }
//                firebaseFunction.updateAny("Product", productDetail.id!!, "listUserLike", productDetail.listUserLike)
//                listLikeSize--
//                listLikeAdapter?.notifyDataSetChanged()
//                numberLikeDetail.text = StringBuilder().append(listLikeSize).append(" people like this")
//                tvLikeCounterDetail.text = StringBuilder().append(listLikeSize).append(" likes")
//                false
//            }
//        }
//
//        buttonAddCartDetail.setOnClickListener {
//            if(!cartAdded) {
//                if(quantity > 0){
//                    if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
//                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                    }
//                    buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_cart_checked)
//                    imageCartCheck.visibility = View.VISIBLE
//                    buttonAddCartDetail.text = StringBuilder().append("ADDED TO CART")
//                    userEstore.value?.cartList?.add(ProductCart(productDetail.id, quantity, colorChoose))
//                    darkButtonDetail.isEnabled = false
//                    lightButtonDetail.isEnabled = false
//
//                    ivAddCartDetail.isEnabled = false
//                    ivMinusCartDetail.isEnabled = false
//                    edtQuantityCartDetail.isEnabled = false
//
//                    cartAdded = true
//                    firebaseFunction.updateAny("Product", productDetail.id!! , "rating", productDetail.rating)
//                    firebaseFunction.updateAny("User", userEstore.value?.id!!, "cartList", userEstore.value?.cartList!!)
//                    Handler().postDelayed({
//                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                    },2000)
//                }else{
//                    Toast.makeText(this, "Number must be larger than 0", Toast.LENGTH_SHORT).show()
//                }
//
//            }else{
//                buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_login)
//                imageCartCheck.visibility = View.INVISIBLE
//                buttonAddCartDetail.text = StringBuilder().append("ADD TO CART")
//                val i = userEstore.value?.cartList?.indexOfFirst { it.idProduct == productDetail.id }
//                userEstore.value?.cartList?.removeAt(i!!)
//                updateUser(userEstore.value!!)
//                firebaseFunction.updateAny("User", userEstore.value?.id!!, "cartList", userEstore.value?.cartList!!)
//
//                darkButtonDetail.isEnabled = true
//                lightButtonDetail.isEnabled = true
//
//                ivAddCartDetail.isEnabled = true
//                ivMinusCartDetail.isEnabled = true
//                edtQuantityCartDetail.isEnabled = true
//
//                cartAdded = false
//            }
//        }
//
//
//
//        ivMinusCartDetail.setOnClickListener {
//            if(quantity > 0){
//                quantity -= 1
//                edtQuantityCartDetail.setText(quantity.toString())
//            }
//        }
//        ivAddCartDetail.setOnClickListener {
//            quantity += 1
//            edtQuantityCartDetail.setText(quantity.toString())
//        }
//
//
//        setUpAdapter(productDetail.listUserLike)
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
        binding.apply {
            star1.setImageResource(R.drawable.ic_star_red_24dp)
            star2.setImageResource(R.drawable.ic_star_red_24dp)
            star3.setImageResource(R.drawable.ic_star_red_24dp)
            star4.setImageResource(R.drawable.ic_star_red_24dp)
            star5.setImageResource(R.drawable.ic_star_red_24dp)
        }
        viewModel.star1click = false
        viewModel.star2click = false
        viewModel.star3click = false
        viewModel.star4click = false
        viewModel.star5click = true
    }

    private fun set4star() {
        binding.apply {
            star1.setImageResource(R.drawable.ic_star_red_24dp)
            star2.setImageResource(R.drawable.ic_star_red_24dp)
            star3.setImageResource(R.drawable.ic_star_red_24dp)
            star4.setImageResource(R.drawable.ic_star_red_24dp)
            star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        }
        viewModel.star1click = false
        viewModel.star2click = false
        viewModel.star3click = false
        viewModel.star4click = true
        viewModel.star5click = false
    }

    private fun set3star() {
        binding.apply {
            star1.setImageResource(R.drawable.ic_star_red_24dp)
            star2.setImageResource(R.drawable.ic_star_red_24dp)
            star3.setImageResource(R.drawable.ic_star_red_24dp)
            star4.setImageResource(R.drawable.ic_star_border_red_24dp)
            star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        }
        viewModel.star1click = false
        viewModel.star2click = false
        viewModel.star3click = true
        viewModel.star4click = false
        viewModel.star5click = false
    }

    private fun set2star() {
        binding.apply {
            star1.setImageResource(R.drawable.ic_star_red_24dp)
            star2.setImageResource(R.drawable.ic_star_red_24dp)
            star3.setImageResource(R.drawable.ic_star_border_red_24dp)
            star4.setImageResource(R.drawable.ic_star_border_red_24dp)
            star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        }
        viewModel.star1click = false
        viewModel.star2click = true
        viewModel.star3click = false
        viewModel.star4click = false
        viewModel.star5click = false
    }

    private fun set1star() {
        binding.apply {
            star1.setImageResource(R.drawable.ic_star_red_24dp)
            star2.setImageResource(R.drawable.ic_star_border_red_24dp)
            star3.setImageResource(R.drawable.ic_star_border_red_24dp)
            star4.setImageResource(R.drawable.ic_star_border_red_24dp)
            star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        }
        viewModel.star1click = true
        viewModel.star2click = false
        viewModel.star3click = false
        viewModel.star4click = false
        viewModel.star5click = false
    }

    private fun resetStar() {
        binding.apply {
            star1.setImageResource(R.drawable.ic_star_border_red_24dp)
            star2.setImageResource(R.drawable.ic_star_border_red_24dp)
            star3.setImageResource(R.drawable.ic_star_border_red_24dp)
            star4.setImageResource(R.drawable.ic_star_border_red_24dp)
            star5.setImageResource(R.drawable.ic_star_border_red_24dp)
        }
        viewModel.star1click = false
        viewModel.star2click = false
        viewModel.star3click = false
        viewModel.star4click = false
        viewModel.star5click = false
    }

    private fun setUpAdapter(listLike: List<String>) {
        listLikeAdapter = ListLikeAdapter(this, listLike)
        rvListLike.adapter = listLikeAdapter
        rvListLike.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}
