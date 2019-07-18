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

    //    var color: List<String>?
    constructor(
        id: String?,
        name: String?,
        price: Int?,
        photoDark: String?,
        photoLight: String?,
        likeCounter: Int?,
        commentCounter: Int?,
        type: String?,
        brand: String?,
        os: String?,
        numberSold: Int?
    ) : this() {
        this.id = id
        this.commentCounter = commentCounter
        this.likeCounter = likeCounter
        this.name = name
        this.photoDark = photoDark
        this.photoLight = photoLight
        this.price = price
        this.type = type
        this.brand = brand
        this.os = os
        this.numberSold = numberSold
    }
}

