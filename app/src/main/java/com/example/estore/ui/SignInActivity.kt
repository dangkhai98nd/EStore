package com.example.estore.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.model.DatabaseEstore
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var firebaseFunction = FirebaseFunction()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mAuth = FirebaseAuth.getInstance()

        buttonSignIn.setOnClickListener {
            authentication(emailInput.text.toString(), passwordInput.text.toString())
        }

        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun authentication(email: String?, password: String?){
        if (email != null && password != null) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { p0 ->
                    if(p0.isSuccessful){
                        val user = mAuth.currentUser

                        if (user != null) {
                            firebaseFunction.estoreGetUserFromId(user.uid)
                            Handler().postDelayed({
//                                userEstore = firebaseFunction.getUser()
                                Log.i("databaseUser", "${userEstore?.id}")
                            }, 2000)

                        }
                        Toast.makeText(baseContext, "Success.", Toast.LENGTH_SHORT).show()

                        firebaseFunction.estoreGetProductAll()
                        DatabaseEstore.getDatabase(this)

                        Handler().postDelayed({
                            Log.i("database", "${database[0]}")
                        }, 2000)
                    }else{
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else {
            Toast.makeText(baseContext, "Please fill in everything", Toast.LENGTH_SHORT).show()
        }
    }
}
