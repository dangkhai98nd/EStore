package com.example.estore.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.R
import com.example.estore.adapter.BrowseAdapter
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.Product
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity() {
    private var searchResultAdapter: BrowseAdapter? = null
    private var keyword: MutableLiveData<String> = MutableLiveData()
    private var productsResult: MutableList<Product> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        initToolbar()
        setupAdapter()
        keyword.observe(this, Observer {
            searchProduct(it)
            if(productsResult.size == 0)
            {
                notFound.visibility = View.VISIBLE
                rvSearchResult.visibility = View.GONE
            }
            else{
                notFound.visibility = View.GONE
                rvSearchResult.visibility = View.VISIBLE
            }
            searchResultAdapter?.setProducts(productsResult)
            tvResult.text = StringBuffer().append("${productsResult.size} result(s) for \"${keyword.value}\"")
        })
        keyword.value = intent.getStringExtra("keyword")

    }

    private fun initToolbar() {
        ic_searchSearch.setOnClickListener {
            groupNormalSearch.visibility = View.GONE
            groupSearchSearch.visibility = View.VISIBLE
        }
        ic_closeSearch.setOnClickListener {
            groupSearchSearch.visibility = View.GONE
            groupNormalSearch.visibility = View.VISIBLE
            edtSearchSearch.text?.clear()
        }
        ic_backSearch.setOnClickListener {
            onBackPressed()
        }
        tvSearchSearch.setOnClickListener {
            if (edtSearchSearch.text.toString() != "") {
                keyword.value = edtSearchSearch.text.toString()
            }
        }
    }

    private fun searchProduct(keyword: String) {
        productsResult.clear()
        database.map {
            if (it.name?.toLowerCase()?.replace(
                    "\\s".toRegex(),
                    ""
                )?.contains(keyword.toLowerCase().replace("\\s".toRegex(), "")) == true
            ) {
                productsResult.add(it)
            }
        }
    }

    private fun setupAdapter() {
        searchResultAdapter = BrowseAdapter(this@SearchResultActivity)
        rvSearchResult.itemAnimator = DefaultItemAnimator()
        rvSearchResult.adapter = searchResultAdapter
        rvSearchResult.layoutManager = LinearLayoutManager(this@SearchResultActivity)
    }
}
