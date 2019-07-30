package com.example.estore.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.estore.R
import com.example.estore.databinding.ActivitySignUpBinding
import com.example.estore.model.User
import com.example.estore.viewmodel.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private var signUpViewModel: SignInViewModel? = null
    private var mActivitySignUpBinding: ActivitySignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initBinding()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

    }

    private fun initBinding() {
        mActivitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpViewModel = SignInViewModel(this, this)
        mActivitySignUpBinding.apply {
            this?.lifecycleOwner = this@SignUpActivity
            this?.signUpViewModel = signUpViewModel
        }
    }
}
