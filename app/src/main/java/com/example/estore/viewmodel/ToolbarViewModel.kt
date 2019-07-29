package com.example.estore.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.estore.ui.SearchResultActivity

class ToolbarViewModel(
    private val mContext : Context
) : ViewModel() {

    var tvTitle : ObservableField<String> = ObservableField("BROWSE")
    var hideTitle : ObservableField<Boolean> = ObservableField(false)
    var hideSearch : ObservableField<Boolean> = ObservableField(false)
    var edtSearch : String? = null
    var hidePaymentConfirmed : ObservableField<Boolean> = ObservableField(true)
    var hideGroupSearch : ObservableField<Boolean> = ObservableField(true)
    var hidervFilter : ObservableField<Boolean> = ObservableField(false)
    var scrollNull : ObservableField<Boolean> = ObservableField(false)

    fun onClicktvSearch() {
        if (edtSearch != "") {
            val intent = Intent(mContext, SearchResultActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("keyword", edtSearch)
            mContext.startActivity(intent)
        }
    }

    fun onClickivSearch() {
        hideSearch.set(true)
        hideTitle.set(true)
        hideGroupSearch.set(false)
    }
    fun onClickivClose() {
        hideSearch.set(false)
        hideTitle.set(false)
        hideGroupSearch.set(true)
    }
    fun postionNavigation(position : Int) {
        when (position) {
            0 -> {
//                paramsAppBarLayout?.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL +
//                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                scrollNull.set(false)
                tvTitle.set("BROWSE")
                hideSearch.set(false)
                hidePaymentConfirmed.set(true)
                hidervFilter.set(false)
            }
            1 -> {
//                paramsAppBarLayout?.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL +
//                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                scrollNull.set(false)
                tvTitle.set("HOT")
                hideSearch.set(false)
                hidePaymentConfirmed.set(true)
                hidervFilter.set(false)
            }
            2 -> {
//                paramsAppBarLayout?.scrollFlags = 0
                scrollNull.set(true)
                tvTitle.set("CART")
                hideSearch.set(true)
                hidePaymentConfirmed.set(false)
                hidervFilter.set(true)
            }
            else -> {
//                paramsAppBarLayout?.scrollFlags = 0
                scrollNull.set(true)
                tvTitle.set("PROFILE")
                hideSearch.set(true)
                hidePaymentConfirmed.set(true)
                hidervFilter.set(true)
            }
        }

        hideTitle.set(false)
        hideGroupSearch.set(true)
    }
}