package com.example.estore.model

class Product() {
    var id: String? = null
    var name: String? = null
    var price: Int? = null
    var photo: String? = null
    var likeCounter: Int? = null
    var commentCounter: Int? = null

    //    var color: List<String>?
    constructor(
        id: String?,
        name: String?,
        price: Int?,
        photo: String?,
        likeCounter: Int?,
        commentCounter: Int?
    ): this() {
        this.id = id
        this.commentCounter = commentCounter
        this.likeCounter = likeCounter
        this.name = name
        this.photo = photo
        this.price = price
    }
}

