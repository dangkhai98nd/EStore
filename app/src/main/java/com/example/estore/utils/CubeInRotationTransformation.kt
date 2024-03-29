package com.example.estore.utils

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs


class CubeInRotationTransformation : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {

        page.cameraDistance = 20000f

        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 0f
            position <= 0 -> {    // [-1,0]
                page.alpha = 1f
                page.pivotX = page.width.toFloat()
                page.rotationY = 90 * abs(position)

            }
            position <= 1 -> {    // (0,1]
                page.alpha = 1f
                page.pivotX = 0f
                page.rotationY = -90 * abs(position)

            }
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0f
        }


    }
}