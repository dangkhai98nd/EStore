package com.example.estore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product

class DetailViewModel : ViewModel() {
    var productDetail = Product()

    val favoriteClick = MutableLiveData<Boolean>()
    val heartClick = MutableLiveData<Boolean>()
    val cartAdded = MutableLiveData<Boolean>()
    val quantity = MutableLiveData<Int>()
    val photoLink = MutableLiveData<String>()
    val rating = MutableLiveData<Int>()
    var trending = MutableLiveData<Boolean>()
    var star1click = false
    var star2click = false
    var star3click = false
    var star4click = false
    var star5click = false
    var colorChoose: String = "dark"

    fun getImage(){
        if(colorChoose == "dark"){
            photoLink.value = productDetail.photoDark
        }else{
            photoLink.value = productDetail.photoLight
        }
    }

    fun getRating(){
        when {
            productDetail.rating == 5 -> rating.value = 5
            productDetail.rating == 4 -> rating.value = 4
            productDetail.rating == 3 -> rating.value = 3
            productDetail.rating == 2 -> rating.value = 2
            productDetail.rating == 1 -> rating.value = 1
            productDetail.rating == 0 -> rating.value = 0
        }
    }

    fun getHeart(){
        heartClick.value = productDetail.listUserLike.contains(userEstore.value?.id)
    }

    fun getTrending(){
        trending.value = productDetail.trending
    }

    fun getFavoriteButton(){
        favoriteClick.value = userEstore.value?.listFavorite?.contains(productDetail.id) == true
    }
}