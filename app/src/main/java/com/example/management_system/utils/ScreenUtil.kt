package com.example.management_system.utils

import android.content.Context

object ScreenUtil {
    /**
     * 获取屏幕高度(px)
     */
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取屏幕宽度(px)
     */
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

}