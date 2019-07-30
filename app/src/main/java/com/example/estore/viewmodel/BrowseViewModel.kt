package com.example.estore.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.estore.FirebaseFunction
import com.example.estore.R
import com.example.estore.model.DatabaseEstore.Companion.database
import com.example.estore.model.DatabaseEstore.Companion.updateUser
import com.example.estore.model.DatabaseEstore.Companion.userEstore
import com.example.estore.model.Product
import com.example.estore.ui.DetailActivity

class BrowseViewModel(
    val product: Product,
    private val mContext: Context
) : ViewModel() {
    var likeCounter: ObservableField<Int> = ObservableField()
    var commentCounter: ObservableField<Int> = ObservableField()
    var icHeart: ObservableField<Drawable> = ObservableField()
    var icFavourite: ObservableField<Drawable> = ObservableField()
    var heart: Boolean = false
    var favourite: Boolean = false

    init {
        likeCounter.set(product.listUserLike.size)
        commentCounter.set(product.commentCounter)
        favourite = userEstore.value?.listFavorite?.contains(product.id) ?: false
        heart = product.listUserLike.contains(userEstore.value?.id)
        if (favourite)
            icFavourite.set(ContextCompat.getDrawable(mContext, R.drawable.ic_favoriteditem))
        else icFavourite.set(ContextCompat.getDrawable(mContext, R.drawable.ic_favoriteditemdisabled))
        if (heart)
            icHeart.set(ContextCompat.getDrawable(mContext, R.drawable.ic_heartitemenabled))
        else icHeart.set(ContextCompat.getDrawable(mContext, R.drawable.ic_heartitemdisabled))
    }

    fun convertInttoStrPrice(price: Number): String {
        return "\$" + "${price}"
    }

    fun getLikeCounter(number: Int): String {
        return "$number likes"
    }

    fun getCommentCounter(number: Int): String {
        return "$number comments"
    }

    fun detail() {
        val intent = Intent(mContext, DetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("position", database.indexOf(product))
        mContext.startActivity(intent)
    }

    fun buttonFavoriteBrowse() {
        val firebaseFunction = FirebaseFunction()
        favourite = if (!favourite) {
            icFavourite.set(ContextCompat.getDrawable(mContext, R.drawable.ic_favoriteditem))
            product.id?.let { it1 -> userEstore.value?.listFavorite?.add(it1) }
            updateUser(userEstore.value!!)
            firebaseFunction.updateAny("User", userEstore.value!!.id!!, "listFavorite", userEstore.value!!.listFavorite)
            true
        } else {
            icFavourite.set(ContextCompat.getDrawable(mContext, R.drawable.ic_favoriteditemdisabled))
            product.id?.let { it1 -> userEstore.value?.listFavorite?.remove(it1) }
            updateUser(userEstore.value!!)
            firebaseFunction.updateAny("User", userEstore.value!!.id!!, "listFavorite", userEstore.value!!.listFavorite)
            false
        }
    }

    fun buttonHeartBrowse() {
        val firebaseFunction = FirebaseFunction()
        heart = if (!heart) {
            icHeart.set(ContextCompat.getDrawable(mContext, R.drawable.ic_heartitemenabled))
            userEstore.value?.id?.let { it1 -> product.listUserLike.add(it1) }
            likeCounter.set(product.listUserLike.size)
            firebaseFunction.updateAny("Product", product.id!!, "listUserLike", product.listUserLike)
            true
        } else {
            icHeart.set(ContextCompat.getDrawable(mContext, R.drawable.ic_heartitemdisabled))
            userEstore.value?.id?.let { it1 -> product.listUserLike.remove(it1) }
            likeCounter.set(product.listUserLike.size)
            firebaseFunction.updateAny("Product", product.id!!, "listUserLike", product.listUserLike)
            false
        }
    }
}