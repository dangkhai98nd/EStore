package com.example.estore.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estore.FirebaseFunction
import com.example.estore.model.DatabaseEstore.Companion.updateUser
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.model.ProductCart

class DetailViewModel : ViewModel() {
    var productDetail = Product()
    val favoriteClick = MutableLiveData<Boolean>()
    val heartClick = MutableLiveData<Boolean>()
    val cartAdded = MutableLiveData<Boolean>()
    val quantity = MutableLiveData<Int>()
    val photoLink = MutableLiveData<String>()
    val rating = MutableLiveData<Int>()
    var trending = MutableLiveData<Boolean>()
    var numberLike = MutableLiveData<Int>()
    var colorChoose = "dark"
    var star1click = false
    var star2click = false
    var star3click = false
    var star4click = false
    var star5click = false
    var index = -1
    private var firebaseFunction = FirebaseFunction()

    init {
        numberLike.value = 0
        quantity.value = 1
        checkCart()
        getImage(colorChoose)
    }

    fun getImage(color: String) {
        if (color == "dark") {
            photoLink.value = productDetail.photoDark
        } else if (color == "light") {
            photoLink.value = productDetail.photoLight
        }
    }

    fun getRating() {
        when {
            productDetail.rating == 5 -> rating.value = 5
            productDetail.rating == 4 -> rating.value = 4
            productDetail.rating == 3 -> rating.value = 3
            productDetail.rating == 2 -> rating.value = 2
            productDetail.rating == 1 -> rating.value = 1
            productDetail.rating == 0 -> rating.value = 0
        }
    }

    fun getHeart() {
        heartClick.value = productDetail.listUserLike.contains(userEstore.value?.id)
    }

    fun getTrending() {
        trending.value = productDetail.trending
    }

    fun getFavoriteButton() {
        favoriteClick.value = userEstore.value?.listFavorite?.contains(productDetail.id) == true
    }

    fun checkCart() {
        index = userEstore.value!!.cartList.indexOfFirst { it.idProduct == productDetail.id }

        if (index >= 0) {
            cartAdded.value = true
            quantity.value = userEstore.value?.cartList!![index].quantity
            val color = userEstore.value?.cartList!![index].color
            if (color != null) {
                colorChoose = color
                getImage(color)
            }
        } else if (index == -1) {
            cartAdded.value = false
        }

    }

    fun addListLike(){
        userEstore.value?.id?.let { productDetail.listUserLike.add(it) }
        firebaseFunction.updateAny("Product", productDetail.id!!, "listUserLike", productDetail.listUserLike)
    }
    fun removeListLike(){
        userEstore.value?.id?.let { productDetail.listUserLike.remove(it) }
        firebaseFunction.updateAny("Product", productDetail.id!!, "listUserLike", productDetail.listUserLike)
    }
    fun addListFavorite(){
        userEstore.value?.listFavorite?.add(productDetail.id!!)
        userEstore.value?.let { updateUser(it) }
        userEstore.value?.listFavorite?.let {
            firebaseFunction.updateAny("User", userEstore.value?.id!!, "listFavorite", it)
        }
    }

    fun removeFavorite(){
        userEstore.value?.listFavorite?.remove(productDetail.id!!)
        userEstore.value?.let { updateUser(it) }
        userEstore.value?.listFavorite?.let {
            firebaseFunction.updateAny("User", userEstore.value?.id!!, "listFavorite", it)
        }
    }

    fun getNumberLike(){
        numberLike.value = productDetail.listUserLike.size
    }

    fun addCart(){
        userEstore.value?.cartList?.add(ProductCart(productDetail.id, quantity.value, colorChoose))
        userEstore.value?.let { updateUser(it) }
        userEstore.value?.cartList?.let {
            firebaseFunction.updateAny("User", userEstore.value?.id!!, "cartList",
                it
            )
        }
    }
    fun removeCart(){
        val i = userEstore.value?.cartList?.indexOfFirst { it.idProduct == productDetail.id }
        userEstore.value?.cartList?.removeAt(i!!)
        userEstore.value?.let { updateUser(it) }
        firebaseFunction.updateAny("User", userEstore.value?.id!!, "cartList", userEstore.value?.cartList!!)

    }

}
//                quantity.value = userEstore.value?.cartList?.get(index)?.quantity!!

//                edtQuantityCartDetail.setText(quantity.toString())
