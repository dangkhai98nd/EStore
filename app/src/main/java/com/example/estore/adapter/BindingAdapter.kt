package com.example.estore.adapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import jp.wasabeef.glide.transformations.BlurTransformation

class BindingAdapter {
    companion object {
        @BindingAdapter("changeScrollFlags")
        @JvmStatic
        fun CollapsingToolbarLayout.setScrollFlags(scrollNull: Boolean) {
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

        @BindingAdapter("isSelected")
        @JvmStatic
        fun TextView.setIsSelected(boolean: Boolean){
            this.isSelected = boolean
        }

    }
}