package com.example.estore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.estore.R
import com.example.estore.model.DatabaseEstore.Companion.listUser
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.User
import kotlinx.android.extensions.LayoutContainer

class ListLikeAdapter(private val mContext: Context, private val listLike: List<String>) : RecyclerView.Adapter<ListLikeAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_like_detail, parent, false))
    }

    override fun getItemCount(): Int {
        return listLike.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(position)
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val likeProfilePhoto = itemView.findViewById<ImageView>(R.id.likeProfilePhoto)

        fun bind(position: Int){
            val idLike = listLike[position]
            val index = listUser.indexOfFirst { it.id == idLike }
            if(index >= 0){
                val user = listUser[index]
                Glide.with(mContext)
                    .load(user.profilePhoto)
                    .apply(RequestOptions.circleCropTransform())
                    .into(likeProfilePhoto)
            }
        }
    }

}