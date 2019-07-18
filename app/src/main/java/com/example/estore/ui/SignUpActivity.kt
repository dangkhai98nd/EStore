package com.example.estore.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.estore.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        buttonSignUp.setOnClickListener {
            signUp()
        }
    }

    fun signUp(){
        mAuth.createUserWithEmailAndPassword(emailSignUpInput.text.toString(), passwordSignUpInput.text.toString())
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
