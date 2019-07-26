package com.example.estore.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.R
import com.example.estore.adapter.HotAdapter
import com.example.estore.model.DatabaseEstore.Companion.databaseFilter

class HotFragment : Fragment() {
    private var root: View? = null
    private var rvHot: RecyclerView? = null
    private var hotAdapter: HotAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_hot, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvHot = root?.findViewById<RecyclerView>(R.id.rvHot)
        hotAdapter = HotAdapter(view.context)
        rvHot?.adapter = hotAdapter
        rvHot?.layoutManager = LinearLayoutManager(view.context)
        hotAdapter?.notifyDataSetChanged()
        databaseFilter.observe(this, Observer {
            rvHot?.layoutManager?.scrollToPosition(0)
            hotAdapter?.notifyDataSetChanged()
        })
    }
}