package com.example.estore.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.estore.R
import com.example.estore.adapter.ListLikeAdapter
import com.example.estore.databinding.ActivityDetailBinding
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.Product
import com.example.estore.viewmodel.DetailViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var listLikeAdapter: ListLikeAdapter? = null
    private lateinit var databaseRef: DatabaseReference
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val behavior = BottomSheetBehavior.from(bottom_sheet)
        intent
        val position = intent.getIntExtra("position", -1)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.productDetail = database[position]

        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
        viewModel.productDetail.id?.let {
            databaseRef.child(it).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    viewModel.productDetail = p0.getValue(Product::class.java)!!
                    viewModel.getNumberLike()
                    setUpAdapter(viewModel.productDetail.listUserLike)
                    listLikeAdapter?.notifyDataSetChanged()

                }
            })
        }





        viewModel.photoLink.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .into(binding.productPhotoDetail)
            Glide.with(this)
                .load(it)
                .into(binding.imageInSheet)
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
            if (it) {
                binding.clTrendingDetail.visibility = View.VISIBLE
            } else binding.clTrendingDetail.visibility = View.GONE
        })
        viewModel.favoriteClick.observe(this, Observer {
            if (it) {
                binding.buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditem)
            } else buttonFavoriteDetail.setImageResource(R.drawable.ic_favoriteditemdisabled)
        })
        viewModel.cartAdded.observe(this, Observer {
            if (it) {
                binding.apply {
                    buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_cart_checked)
                    buttonAddCartDetail.text = StringBuilder().append("ADDED TO CART")
                    imageCartCheck.visibility = View.VISIBLE
                    darkButtonDetail.isEnabled = false
                    lightButtonDetail.isEnabled = false
                    ivAddCartDetail.isEnabled = false
                    ivMinusCartDetail.isEnabled = false
                    edtQuantityCartDetail.isEnabled = false
                    if (viewModel.colorChoose == "dark") {
                        darkButtonDetail.isChecked = true
                    } else lightButtonDetail.isChecked = true

                    if (behavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    Handler().postDelayed({
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }, 2000)
                }
            } else {
                binding.apply {
                    viewModel.colorChoose.let { it1 -> viewModel.getImage(it1) }
                    buttonAddCartDetail.setBackgroundResource(R.drawable.ic_button_login)
                    buttonAddCartDetail.text = StringBuilder().append("ADD TO CART")
                    imageCartCheck.visibility = View.INVISIBLE
                    darkButtonDetail.isEnabled = true
                    lightButtonDetail.isEnabled = true
                    ivAddCartDetail.isEnabled = true
                    ivMinusCartDetail.isEnabled = true
                    edtQuantityCartDetail.isEnabled = true
                    if (viewModel.colorChoose == "dark") {
                        darkButtonDetail.isChecked = true
                    } else lightButtonDetail.isChecked = true
                }
            }
        })
        viewModel.quantity.observe(this, Observer {
            binding.edtQuantityCartDetail.text = it.toString()
        })
        viewModel.numberLike.observe(this, Observer {
            binding.tvLikeCounterDetail.text = StringBuilder().append(it).append(" likes")
            binding.numberLikeDetail.text = StringBuilder().append(it).append(" people like this")
        })


        viewModel.checkCart()
        viewModel.getRating()
        viewModel.getHeart()
        viewModel.getTrending()
        viewModel.getFavoriteButton()
        viewModel.getNumberLike()

        initToolbar()

        binding.apply {
            nameInSheet.text = viewModel.productDetail.name
            priceInSheet.text = StringBuilder().append("$").append(viewModel.productDetail.price)
            tvProductNameDetail.text = viewModel.productDetail.name
            tvProductNameDetail.isSelected = true
            tvProductPriceDetail.text =
                StringBuilder().append("$").append(viewModel.productDetail.price)
            tvCommentCounterDetail.text = StringBuilder().append(viewModel.productDetail.commentCounter).append(" comments")
        }

        binding.darkButtonDetail.setOnClickListener {
            viewModel.colorChoose = "dark"
            binding.lightButtonDetail.isChecked = false
            viewModel.getImage("dark")
        }

        binding.lightButtonDetail.setOnClickListener {
            viewModel.colorChoose = "light"
            binding.darkButtonDetail.isChecked = false
            viewModel.getImage("light")
        }

        binding.star5.setOnClickListener {
            if (viewModel.star5click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 5
            }
            viewModel.getRating()
        }

        binding.star4.setOnClickListener {
            if (viewModel.star4click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 4
            }
            viewModel.getRating()
        }

        binding.star3.setOnClickListener {
            if (viewModel.star3click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 3
            }
            viewModel.getRating()
        }

        binding.star2.setOnClickListener {
            if (viewModel.star2click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 2
            }
            viewModel.getRating()
        }

        binding.star1.setOnClickListener {
            if (viewModel.star1click) {
                viewModel.productDetail.rating = 0
            } else {
                viewModel.productDetail.rating = 1
            }
            viewModel.getRating()
        }

        binding.apply{
            buttonAddCartDetail.setOnClickListener {
                if (viewModel.cartAdded.value == false) {
                    viewModel.cartAdded.value = true

                } else {
                    viewModel.cartAdded.value = false
                }
            }
        }


        binding.ivAddCartDetail.setOnClickListener {
            viewModel.quantity.value = viewModel.quantity.value?.plus(1)
        }
        binding.ivMinusCartDetail.setOnClickListener {
            if (viewModel.quantity.value!! > 1) {
                viewModel.quantity.value = viewModel.quantity.value?.minus(1)
            }
        }

        binding.buttonHeartDetail.setOnClickListener {
            if (viewModel.heartClick.value == false) {
                viewModel.heartClick.value = true
                viewModel.addListLike()

            } else {
                viewModel.heartClick.value = false
                viewModel.removeListLike()
            }
        }
        binding.buttonFavoriteDetail.setOnClickListener {
            if (viewModel.favoriteClick.value == false) {
                viewModel.favoriteClick.value = true
                viewModel.addListFavorite()
            } else {
                viewModel.favoriteClick.value = false
                viewModel.removeFavorite()
            }
        }

        binding.buttonAddCartDetail.setOnClickListener {
            if(viewModel.cartAdded.value == true){
                viewModel.removeCart()
                viewModel.cartAdded.value = false
            }else {
                viewModel.addCart()
                viewModel.cartAdded.value = true
            }
        }

        setUpAdapter(viewModel.productDetail.listUserLike)


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
        binding.rvListLike.adapter = listLikeAdapter
        binding.rvListLike.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}
