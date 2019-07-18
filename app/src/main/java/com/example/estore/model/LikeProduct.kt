package com.example.estore.model

class LikeProduct() {
    var idUser: String? = null
    var idProductList: MutableList<String>? = null

    constructor(
        idUser: String?,
        idProductList: MutableList<String>?
    ): this(){
        this.idUser = idUser
        this.idProductList = idProductList
    }
}