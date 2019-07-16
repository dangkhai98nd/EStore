package com.example.estore.model

class User() {
    var id: String? = null
    var userName: String? = null
    var email: String? = null
    var password: String? = null

    constructor(id: String?, userName: String?, email: String?, password: String?) : this() {
        this.id = id
        this.userName = userName
        this.email = email
        this.password = password
    }
}