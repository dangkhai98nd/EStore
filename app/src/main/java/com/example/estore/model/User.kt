package com.example.estore.model

class User() {
    var id: String? = null
    var userName: String? = null

    constructor(id: String?, userName: String?) : this() {
        this.id = id
        this.userName = userName
    }
}