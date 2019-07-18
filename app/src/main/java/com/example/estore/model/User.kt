package com.example.estore.model

class User() {
    var id: String? = null
    var userName: String? = null
    var cartList: MutableList<String>? = null
    var listFavorite: MutableList<String>? = null

    constructor(id: String?, userName: String?, cartList: MutableList<String>?, listFavorite: MutableList<String>?) : this() {
        this.id = id
        this.userName = userName
        this.cartList = cartList
        this.listFavorite = listFavorite
    }
}