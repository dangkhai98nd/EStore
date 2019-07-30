package com.example.estore.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.User
import com.example.estore.ui.MainActivity
import com.example.estore.ui.SignUpActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInViewModel(
    private val mContext: Context,
    private val lifecycleOwner: LifecycleOwner
) : ViewModel() {
    var username: String? = null
    var password: String? = null
    var email : String? = null
    private var mAuth: FirebaseAuth? = null
    private var databaseRef: DatabaseReference? = null
    var hideProgressbar : ObservableField<Boolean> = ObservableField(true)
    var hideButtonSignIn : ObservableField<Boolean> = ObservableField(true)
    var notice : ObservableField<String> = ObservableField("")

    fun onClickSignIn() {
        hideProgressbar.set(false)
        hideButtonSignIn.set(true)
        mAuth = FirebaseAuth.getInstance()
        authentication(email, password)
    }

    fun onClickChangeActivitySignUp() {
        val intent = Intent(mContext, SignUpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    }

    fun onClickSignUp() {
        mAuth = FirebaseAuth.getInstance()
        signUp(username ,email, password)
    }

    private fun authentication(email: String?, password: String?) {
        if (email != null && password != null) {
            mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        val user = mAuth?.currentUser
                        if (user != null) {
                            DatabaseEstore.getDatabase(lifecycleOwner, user.uid)
                        }
                        notice.set("Success")

                        DatabaseEstore.signInCheck.observe(lifecycleOwner, Observer {
                            val intent = Intent(mContext, MainActivity::class.java)
                            Log.e("size", "${DatabaseEstore.database.size}")
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            mContext.startActivity(intent)
                        })

                    } else {
                        hideProgressbar.set(true)
                        hideButtonSignIn.set(false)
                        notice.set("Authentication failed")
                    }
                }
        } else {
            hideProgressbar.set(true)
            hideButtonSignIn.set(false)
            notice.set("Please fill in everything")
        }
    }

    private fun signUp(username: String?,email: String?, password: String?){
        if (password != null && email != null) {
            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { p0 ->
                    if(p0.isSuccessful){
                        databaseRef = FirebaseDatabase.getInstance().getReference("User")
                        val uid = mAuth?.currentUser?.uid
                        if (uid != null) {
                            databaseRef?.child(uid)?.setValue(User(uid, username, ArrayList(), ArrayList(), null, null))
                        }

                    }else{
                        notice.set("Sign up failed, try again.")
                    }
                }
        }else {
            notice.set("Please fill in everything")
        }
    }

}