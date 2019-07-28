package com.example.estore.model

class Product() {
    var id: String? = null
    var name: String? = null
    var price: Int? = null
    var photoDark: String? = null
    var photoLight: String? = null
    var commentCounter: Int? = null
    var type: String? = null //phone? table? watch? headphones?
    var brand: String? = null //samsung? apple? oppo? xiaomi? beats? skullcandy?
    var os: String? = null // android? ios? null?
    var numberSold: Int? = null
    var trending: Boolean? = null
    var listUserLike: MutableList<String> = ArrayList()
    var rating: Int = 0
    var time: String? = null

    constructor(
        id: String?,
        name: String?,
        price: Int?,
        photoDark: String?,
        photoLight: String?,
        commentCounter: Int?,
        type: String?, //phone? table? watch? headphones?
        brand: String?, //samsung? apple? oppo? xiaomi? beats? skullcandy?
        os: String?, // android? ios? null?
        numberSold: Int?,
        trending: Boolean?,
        listUserLike: MutableList<String>,
        rating: Int,
        time: String?
    ) : this() {
        this.id = id
        this.name = name
        this.price = price
        this.photoDark = photoDark
        this.photoLight = photoLight
        this.commentCounter = commentCounter
        this.type = type
        this.brand = brand
        this.os = os
        this.numberSold = numberSold
        this.trending = trending
        this.listUserLike.addAll(listUserLike)
        this.rating = rating
        this.time = time
    }


}

