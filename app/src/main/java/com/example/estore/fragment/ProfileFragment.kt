package com.example.estore.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.estore.R
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.ui.SignInActivity
//import com.example.estore.ui.SignInActivity.Companion.database
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userName.text = userEstore?.id
        profileStatus.text = userEstore?.status

        Glide.with(this)
            .load(userEstore?.profilePhoto)
            .into(profilePhoto)

        buttonLogout.setOnClickListener {
            val intent = Intent(context, SignInActivity::class.java)
            database.clear()
            userEstore = null
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}