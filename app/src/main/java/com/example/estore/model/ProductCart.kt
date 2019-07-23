package com.example.estore.model

class ProductCart (var idProduct: String? = null,
                   var quantity: Int? = null,
                   var color: String? = null){
    constructor(idProduct: String?): this(){
        this.idProduct = idProduct
    }
}


//class ProductCart {
//    var idProduct: String? = null
//    var quantity: Int? = null
//    var color: String? = null
//}