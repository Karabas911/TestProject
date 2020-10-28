package com.example.testproject.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkConnected(): Boolean {
    val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        val activeNetwork = it.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
    return false
}
