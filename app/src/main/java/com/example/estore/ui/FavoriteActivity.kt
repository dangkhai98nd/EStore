package com.example.estore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.R
import com.example.estore.adapter.FavoriteUserAdapter
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private var listUserFavoriteAdapter: FavoriteUserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initToolbar()
        userEstore.value?.listFavorite?.let { setUpAdapter(it) }
    }

    private fun initToolbar() {
        ic_backFavorite.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpAdapter(listFavorite: List<String>){
        listUserFavoriteAdapter = FavoriteUserAdapter(this, listFavorite)
        rvListUserFavorite.adapter = listUserFavoriteAdapter
        rvListUserFavorite.layoutManager = LinearLayoutManager(this)
    }
}
