package com.example.estore.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.adapter.BrowseAdapter
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.databaseFilter
import com.example.estore.model.Product
import kotlinx.android.synthetic.main.fragment_browse.*

class BrowseFragment : Fragment() {
    private var browseAdapter : BrowseAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_browse,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter(view)
        Handler().postDelayed({
            browseAdapter?.setProducts(databaseFilter.value ?: emptyList())

        },2000)
        databaseFilter.observe(this, Observer {
            browseAdapter?.setProducts(it)
        })

    }

    private fun setupAdapter(view : View) {
        browseAdapter = BrowseAdapter(view.context)
        rvBrowse.itemAnimator =DefaultItemAnimator()
        rvBrowse.adapter = browseAdapter
        rvBrowse.layoutManager = LinearLayoutManager(view.context)
    }
}