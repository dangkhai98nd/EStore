package com.example.estore.ui

import android.os.Bundle
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

        mAuth = FirebaseAuth.getInstance()
        buttonSignUp.setOnClickListener {
            signUp(emailSignUpInput.text.toString(), passwordSignUpInput.text.toString())
        }
    }

    private fun signUp(email: String?, password: String?){
        if (password != null && email != null) {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { p0 ->
                    if(p0.isSuccessful){
//                        val user = mAuth.currentUser
//                        println(user?.uid)
//                        Toast.makeText(baseContext, "Success.", Toast.LENGTH_SHORT).show()

                        databaseRef = FirebaseDatabase.getInstance().getReference("User")
                        val uid = mAuth.currentUser?.uid
                        if (uid != null) {
                            databaseRef.child(uid).setValue(User(uid, null, ArrayList(), ArrayList(), null, null))
                        }

                    }else{
                        Toast.makeText(baseContext, "Sign up failed, try again.", Toast.LENGTH_SHORT).show()
                    }
                }
        }else {
            Toast.makeText(baseContext, "Please fill in everything", Toast.LENGTH_SHORT).show()
        }
    }
}
