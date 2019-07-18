package com.example.estore.model

class Product {
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


}

