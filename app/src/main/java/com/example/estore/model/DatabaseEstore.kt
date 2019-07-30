package com.example.estore.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.estore.FirebaseFunction
import java.text.FieldPosition

class DatabaseEstore {
    companion object {
        var database : MutableList<Product> = mutableListOf()
        var databaseFilter : MutableLiveData<List<Product>> = MutableLiveData()
        var userEstore: MutableLiveData<User> = MutableLiveData()
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
                userEstore.value = it
            })

            firebaseFunction.listUserLiveData.observe(lifecycleOwner, Observer {
                listUser.addAll(it)
            })

            firebaseFunction.estoreGetUserFromId(idUser)
            firebaseFunction.estoreGetUserAll()
            firebaseFunction.estoreGetProductAll()
        }

        fun updateUser(user : User?) {
            userEstore.value = user
        }
    }


}