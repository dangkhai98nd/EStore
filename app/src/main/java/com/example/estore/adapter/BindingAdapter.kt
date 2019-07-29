package com.example.estore.adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

class BindingAdapter {
    companion object {
        @BindingAdapter("changeScrollFlags")
        @JvmStatic
        fun CollapsingToolbarLayout.setScrollFlags(scrollNull: Boolean) {
            Log.e("setScrollFlags null", "$scrollNull")
            val paramsAppBarLayout: AppBarLayout.LayoutParams = this.layoutParams as AppBarLayout.LayoutParams
            if (scrollNull) {
                paramsAppBarLayout.scrollFlags = 0
            } else {
                paramsAppBarLayout.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL +
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            }
            this.layoutParams = paramsAppBarLayout
        }

        @BindingAdapter("imageURL")
        @JvmStatic
        fun ImageView.setImageView(url: String) {
            Glide.with(context)
                .load(url)
                .into(this)
        }

//        @BindingAdapter("colorProduct")
//        @JvmStatic
//        fun CardView.setColorProduct(color : String?) {
//            if (color == "dark")
//            {
//                this.setCardBackgroundColor()
//            }
//        }
    }
}