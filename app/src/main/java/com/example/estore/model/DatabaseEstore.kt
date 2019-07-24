package com.example.estore.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.estore.FirebaseFunction

class DatabaseEstore {
    companion object {
        var database : MutableList<Product> = mutableListOf()
        var userEstore: User? = null
        var listUser: MutableList<User> = mutableListOf()
        fun getDatabase(lifecycleOwner: LifecycleOwner, idUser: String ){
            val firebaseFunction = FirebaseFunction()
            firebaseFunction.productLiveData.observe(lifecycleOwner , Observer {
                database.addAll(it)
//            Log.e("observe size","${products.size}")
//            Toast.makeText(view.context,"${products[0].name}",Toast.LENGTH_SHORT).show()
            })

            firebaseFunction.userLiveData.observe(lifecycleOwner, Observer {
                userEstore = it
            })

            firebaseFunction.listUserLiveData.observe(lifecycleOwner, Observer {
                listUser.addAll(it)
            })

            firebaseFunction.estoreGetProductAll()
            firebaseFunction.estoreGetUserFromId(idUser)
            firebaseFunction.estoreGetUserAll()
        }

    }
}