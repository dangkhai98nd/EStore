package com.example.estore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.estore.model.Product
import com.example.estore.model.User
import com.google.firebase.database.*

class FirebaseFunction {
    private lateinit var databaseRef: DatabaseReference
    var productLiveData: MutableLiveData<List<Product>> = MutableLiveData()
    var userLiveData: MutableLiveData<User> = MutableLiveData()
    var listUserLiveData: MutableLiveData<List<User>> = MutableLiveData()
    private var listProduct: MutableList<Product> = ArrayList()
    private var listUser: MutableList<User> = ArrayList()

    fun estoreGetProductAll() {
        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (product in p0.children) {
                    product.getValue(Product::class.java)?.let { listProduct.add(it) }
                }
                productLiveData.value = listProduct
            }

        })

    }

    fun estoreGetUserFromId(id: String) {
        databaseRef = FirebaseDatabase.getInstance().getReference("User")
        databaseRef.orderByChild("id").equalTo(id)
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//                    val user = p0.getValue(User::class.java)
                    userLiveData.value = p0.getValue(User::class.java)
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
            })
    }

    fun estoreGetUserAll() {
        databaseRef = FirebaseDatabase.getInstance().getReference("User")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (product in p0.children) {
                    product.getValue(User::class.java)?.let { listUser.add(it) }
                }
                listUserLiveData.value = listUser
            }

        })
    }

    //////////////////////////////

    fun updateProduct(product: Product, path: String) {
        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
        product.id?.let { databaseRef.child(it).child(path).setValue(product) }
    }

    fun updateUser(user: User) {
        databaseRef = FirebaseDatabase.getInstance().getReference("User")
        user.id?.let { databaseRef.child(it).setValue(user) }
    }

    fun updateAny(pathRef: String, id: String, stringChild: String, value: Any) {
        databaseRef = FirebaseDatabase.getInstance().getReference(pathRef)
        databaseRef.child(id).child(stringChild).setValue(value)
    }

}

