package com.example.estore.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.estore.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mAuth = FirebaseAuth.getInstance()

        buttonSignIn.setOnClickListener {
            authentication()
        }

        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun authentication(){
        mAuth.signInWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
            .addOnCompleteListener(this) { p0 ->
                if(p0.isSuccessful){
                    val user = mAuth.currentUser
                    println(user?.uid)
                    Toast.makeText(baseContext, "Success.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
