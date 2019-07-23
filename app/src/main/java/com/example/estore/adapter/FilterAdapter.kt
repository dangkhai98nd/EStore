package com.example.estore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.estore.R
import kotlinx.android.extensions.LayoutContainer

class FilterAdapter (
    private val mContext : Context
) : RecyclerView.Adapter<FilterAdapter.ItemFilterViewHolder>() {
    private val listFilter : List<String> = listOf("All","Trending","New","Price")
    private var selectedPosition : Int = 0
    override fun getItemCount(): Int = listFilter.size

    override fun onBindViewHolder(holder: ItemFilterViewHolder, position: Int) {
        holder.tvFilter.text = listFilter[position]
        if (position == selectedPosition)
        {
            holder.cvFilter.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.colorRed))
        }
        else {
            holder.cvFilter.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.colorNull))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFilterViewHolder {
        return ItemFilterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter,parent,false))
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    class ItemFilterViewHolder (
        override val containerView: View
    ): RecyclerView.ViewHolder(containerView) , LayoutContainer{
        var tvFilter: TextView = containerView.findViewById(R.id.tvFilter)
        var cvFilter = containerView.findViewById<CardView>(R.id.cvFilter)
    }


}