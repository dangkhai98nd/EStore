package com.example.estore.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.estore.R
import com.example.estore.databinding.ActivitySignInBinding
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.getDatabase
import com.example.estore.model.DatabaseEstore.Companion.signInCheck
import com.example.estore.viewmodel.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var signInViewModel: SignInViewModel? = null
    private var mActivitySignInBinding: ActivitySignInBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initBinding()
        val slideUp: Animation = loadAnimation(this, R.anim.move_up)
//        val fadeIn: Animation = loadAnimation(this, R.anim.fade_in)
        Glide.with(this)
            .load(R.drawable.ic_logo_2)
            .into(uselessLogo)
        Handler().postDelayed({
            uselessLogo.visibility = View.GONE
            logoSignIn.visibility = View.VISIBLE
            linear1Si.visibility = View.VISIBLE
            linear2Si.visibility = View.VISIBLE
            signInViewModel?.hideButtonSignIn?.set(false)
            Glide.with(this)
                .load(R.drawable.ic_logo_still)
                .into(logoSignIn)
            logoSignIn.startAnimation(slideUp)
            linear1Si.startAnimation(slideUp)
            linear2Si.startAnimation(slideUp)
            buttonSignIn.startAnimation(slideUp)
        }, 3600)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }

    fun initBinding() {
        mActivitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        signInViewModel = SignInViewModel(this, this)
        mActivitySignInBinding.apply {
            this?.lifecycleOwner = this@SignInActivity
            this?.signInViewModel = signInViewModel
        }
    }
}

