package com.example.estore.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.estore.FirebaseFunction
import com.google.firebase.database.*

class DatabaseEstore {
    companion object {
        var database : MutableList<Product> = mutableListOf()
        var userEstore: User? = null
        private lateinit var databaseRef: DatabaseReference
        fun getDatabase(lifecycleOwner: LifecycleOwner,idUser : String) {
            val firebaseFunction = FirebaseFunction()
            firebaseFunction.productLiveData.observe(lifecycleOwner , Observer {
                database.add(it)
//            Log.e("observe size","${products.size}")
//            Toast.makeText(view.context,"${products[0].name}",Toast.LENGTH_SHORT).show()
            })

            firebaseFunction.userLiveData.observe(lifecycleOwner, Observer {
                userEstore = it
            })

            firebaseFunction.estoreGetProductAll()
            firebaseFunction.estoreGetUserFromId(idUser)
        }

    }
}