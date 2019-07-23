package com.example.estore.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.estore.R
import com.example.estore.adapter.BrowseAdapter
import com.example.estore.model.DatabaseEstore.Companion.database
import kotlinx.android.synthetic.main.fragment_browse.*

class BrowseFragment : Fragment() {
//    private val firebaseFunction = FirebaseFunction()
    private var browseAdapter : BrowseAdapter? = null
//    private var products : List<Product> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_browse,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupAdapter(view)
        browseAdapter?.addAll(database)
//        firebaseFunction.listProductLiveData.observe(this, Observer {
//            products = it
//            Log.e("observe size","${products.size}")
//            Toast.makeText(view.context,"${products[0].name}",Toast.LENGTH_SHORT).show()
//        })
//        firebaseFunction.estoreGetProductAll()


    }

    private fun setupAdapter(view : View) {
        browseAdapter = BrowseAdapter(view.context)
        rvBrowse.itemAnimator =DefaultItemAnimator()
        rvBrowse.adapter = browseAdapter
        rvBrowse.layoutManager = LinearLayoutManager(view.context)
    }
}