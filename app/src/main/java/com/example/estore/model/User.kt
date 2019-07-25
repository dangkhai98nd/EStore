package com.example.estore.model

class User() {
    var id: String? = null
    var userName: String? = null
    var cartList: MutableList<ProductCart> = ArrayList()
    var listFavorite: MutableList<String> = ArrayList()
    var status: String? = null
    var profilePhoto: String? = null

    constructor(
        id: String?,
        userName: String?,
        cartList: MutableList<ProductCart>,
        listFavorite: MutableList<String>,
        status: String?,
        profilePhoto: String?
    ) : this() {
        this.id = id
        this.userName = userName
        this.cartList.addAll(cartList)
        this.listFavorite.addAll(listFavorite)
        this.status = status
        this.profilePhoto = profilePhoto
    }
}