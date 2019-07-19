package com.example.estore.model

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.estore.FirebaseFunction

class DatabaseEstore {
    companion object {
        var database : MutableList<Product> = mutableListOf()
        fun getDatabase(lifecycleOwner: LifecycleOwner) {
            val firebaseFunction = FirebaseFunction()
            firebaseFunction.productLiveData.observe(lifecycleOwner , Observer {
                database.add(it)
//            Log.e("observe size","${products.size}")
//            Toast.makeText(view.context,"${products[0].name}",Toast.LENGTH_SHORT).show()
            })
            firebaseFunction.estoreGetProductAll()
        }

        var userEstore: User? = null
    }
}