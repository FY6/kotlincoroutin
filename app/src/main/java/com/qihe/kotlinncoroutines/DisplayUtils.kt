package com.qihe.kotlinncoroutines

import android.content.Context
import android.util.TypedValue
import android.view.View

object DisplayUtils {
    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    @JvmStatic
    fun getScreenWidget(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }
}

fun View.dp2px(dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            resources.displayMetrics).toInt()
}
