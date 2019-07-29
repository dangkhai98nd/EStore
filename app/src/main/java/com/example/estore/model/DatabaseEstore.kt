package com.example.estore.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.estore.FirebaseFunction

class DatabaseEstore {
    companion object {
        var database : MutableList<Product> = mutableListOf()
        var databaseFilter : MutableLiveData<List<Product>> = MutableLiveData()
        var userEstore: User? = null
        var listUser: MutableList<User> = mutableListOf()
        var signInCheck: MutableLiveData<Boolean> = MutableLiveData()

        fun getDatabase(lifecycleOwner: LifecycleOwner, idUser: String ){
            val firebaseFunction = FirebaseFunction()
            firebaseFunction.productLiveData.observe(lifecycleOwner , Observer {
                database.addAll(it)
                databaseFilter.value = it
            })

            firebaseFunction.userLiveData.observe(lifecycleOwner, Observer {
                signInCheck.value = true
                userEstore = it
            })

            firebaseFunction.listUserLiveData.observe(lifecycleOwner, Observer {
                listUser.addAll(it)
            })

            firebaseFunction.estoreGetUserFromId(idUser)
            firebaseFunction.estoreGetUserAll()
            firebaseFunction.estoreGetProductAll()
        }

    }
}