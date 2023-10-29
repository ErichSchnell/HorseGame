package com.example.horsechallenge.model

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object DimensionsScreen {
    fun getScreenDimensions(context: Context): Pair<Float, Float> {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val widthDp = displayMetrics.widthPixels / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        val heightDp = displayMetrics.heightPixels / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

        return Pair(widthDp, heightDp)
    }
}