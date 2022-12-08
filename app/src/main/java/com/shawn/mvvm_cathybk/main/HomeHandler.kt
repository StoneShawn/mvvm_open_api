package com.shawn.mvvm_cathybk.main

import com.shawn.network.model.Attraction

interface HomeHandler {
    fun goHome()
    fun goDetail(data: Attraction)
    fun goWebView(url: String)
    fun onBack()
}