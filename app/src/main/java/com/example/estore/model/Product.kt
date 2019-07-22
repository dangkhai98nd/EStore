package com.example.estore.model

class Product() {
    var id: String? = null
    var name: String? = null
    var price: Int? = null
    var photoDark: String? = null
    var photoLight: String? = null
    var likeCounter: Int? = null
    var commentCounter: Int? = null
    var type: String? = null //phone? table? watch? headphones?
    var brand: String? = null //samsung? apple? oppo? xiaomi? beats? skullcandy?
    var os: String? = null // android? ios? null?
    var numberSold: Int? = null
    var trending: Boolean? = null
    var listUserLike: MutableList<String>? = null
    var rating: Int? = null

    constructor(
        id: String? = null,
        name: String? = null,
        price: Int? = null,
        photoDark: String? = null,
        photoLight: String? = null,
        likeCounter: Int? = null,
        commentCounter: Int? = null,
        type: String? = null, //phone? table? watch? headphones?
        brand: String? = null, //samsung? apple? oppo? xiaomi? beats? skullcandy?
        os: String? = null, // android? ios? null?
        numberSold: Int? = null,
        trending: Boolean? = null,
        listUserLike: MutableList<String>? = null,
        rating: Int? = null
    ) : this() {

        this.id = id
        this.name = name
        this.price = price
        this.photoDark = photoDark
        this.photoLight = photoLight
        this.likeCounter = likeCounter
        this.commentCounter = commentCounter
        this.type = type
        this.brand = brand
        this.os = os
        this.numberSold = numberSold
        this.trending = trending
        this.listUserLike = listUserLike
        this.rating = rating
    }


}

