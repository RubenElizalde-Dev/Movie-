package com.bluelabs.moviesapp.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager

class InternetUtils {
    fun isNetworkAvailable(activity: Activity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}