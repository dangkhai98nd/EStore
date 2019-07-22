package com.example.estore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estore.model.LikeProduct
import com.example.estore.model.Product
import com.example.estore.model.User
import com.google.firebase.database.*

class FirebaseFunction : ViewModel() {
    private lateinit var databaseRef: DatabaseReference
    var productLiveData : MutableLiveData<Product> = MutableLiveData()
    var userLiveData : MutableLiveData<User> = MutableLiveData()
    private var listProduct: MutableList<Product> = ArrayList()
//    private var user: User? = null
//    private var productLike: LikeProduct? = null

    fun estoreGetProductFilter(filter: String) {
        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
//            .startAt(price.toDouble())
        databaseRef.orderByChild(filter).addChildEventListener(object :
            ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val product = p0.getValue(Product::class.java)
                if (product != null) {
                    listProduct.add(product)
                    productLiveData.value = product
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }

    fun estoreGetProductAll() {
        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
        databaseRef.addChildEventListener(object :
            ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val product = p0.getValue(Product::class.java)
                if (product != null) {
//                    listProduct.add(product)
                    productLiveData.value = product
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

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

//    fun getUser(): User? {
//        return user
//    }

    fun getListProduct(): MutableList<Product> {
        return listProduct
    }

    //////////////////////////////

    fun updateProduct(product: Product){
        databaseRef = FirebaseDatabase.getInstance().getReference("Product")
        product.id?.let { databaseRef.child(it).setValue(product) }
    }

    fun updateUser(user: User){
        databaseRef = FirebaseDatabase.getInstance().getReference("User")
        user.id?.let { databaseRef.child(it).setValue(user) }
    }

//    fun estoreGetProductUserLike(idUser: String) {
//        databaseRef = FirebaseDatabase.getInstance().getReference("LikeProduct")
//        databaseRef.orderByChild("idUser").equalTo(idUser).addChildEventListener(object : ChildEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//            }
//
//            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//            }
//
//            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//                productLike = p0.getValue(LikeProduct::class.java)
//            }
//
//            override fun onChildRemoved(p0: DataSnapshot) {
//
//            }
//        })
//    }
//
//    fun getListProductLike(): LikeProduct? {
//        return productLike
//    }

}