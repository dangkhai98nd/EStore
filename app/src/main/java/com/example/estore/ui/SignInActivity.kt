package com.example.estore.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.estore.R
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.databaseFilter
import com.example.estore.model.DatabaseEstore.Companion.getDatabase
import com.example.estore.model.DatabaseEstore.Companion.listUser
import com.example.estore.model.DatabaseEstore.Companion.signInCheck
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val slideUp: Animation = loadAnimation(this, R.anim.move_up)
        Glide.with(this)
            .load(R.drawable.ic_e_logo_transparent)
            .into(logoSignIn)
        logoSignIn.startAnimation(slideUp)
        Handler().postDelayed({
            Glide.with(this)
                .load(R.drawable.ic_logo)
                .into(logoSignIn)
            val fadeIn: Animation = loadAnimation(this, R.anim.fade_in)
            logoSignIn.visibility = View.VISIBLE
            linear1Si.visibility = View.VISIBLE
            linear2Si.visibility = View.VISIBLE

            linear1Si.startAnimation(fadeIn)
            linear2Si.startAnimation(fadeIn)
            buttonSignIn.startAnimation(fadeIn)
        }, 3600)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        mAuth = FirebaseAuth.getInstance()
        buttonSignIn.setOnClickListener {
            authentication(emailInput.text.toString(), passwordInput.text.toString())
        }

        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun authentication(email: String?, password: String?) {
        if (email != null && password != null) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { p0 ->
                    if (p0.isSuccessful) {
                        val user = mAuth.currentUser

                        if (user != null) {
                            getDatabase(this, user.uid)
                        }
                        Toast.makeText(baseContext, "Success.", Toast.LENGTH_SHORT).show()
                        signInCheck.observe(this, Observer {
                            val intent = Intent(this, MainActivity::class.java)
                            Log.e("size", "${database.size}")
                            startActivity(intent)
                        })

                    } else {
                        Toast.makeText(applicationContext, "Authentication failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } else {
            Toast.makeText(applicationContext, "Please fill in everything", Toast.LENGTH_SHORT).show()
        }
    }
}
