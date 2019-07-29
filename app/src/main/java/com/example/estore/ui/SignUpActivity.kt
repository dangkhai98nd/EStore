package com.example.estore.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.estore.R
import com.example.estore.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        mAuth = FirebaseAuth.getInstance()
        buttonSignUp.setOnClickListener {
            if (!TextUtils.isEmpty(emailSignUpInput.text) && !TextUtils.isEmpty(passwordSignUpInput.text) && !TextUtils.isEmpty(userNameSignUpInput.text)) {
                signUpFirebase(emailSignUpInput.text.toString(), passwordSignUpInput.text.toString(), userNameSignUpInput.text.toString())
                buttonSignUp.isEnabled = false
            } else {
                Toast.makeText(applicationContext, "Please fill in everything", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signUpFirebase(email: String, password: String, userName: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { p0 ->
                if (p0.isSuccessful) {

                    Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()

                    databaseRef = FirebaseDatabase.getInstance().getReference("User")
                    val uid = mAuth.currentUser?.uid
                    if (uid != null) {
                        databaseRef.child(uid)
                            .setValue(User(uid, userName, ArrayList(), ArrayList(), null, null))
                    }

                } else {
                    Toast.makeText(applicationContext, "Sign up failed, try again", Toast.LENGTH_SHORT).show()
                    buttonSignUp.isEnabled = true
                }

            }
    }
}
