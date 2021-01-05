package com.example.management_system.http.api

import com.zhan.mvvm.http.RetrofitFactory

object ServiceFactory {

    val apiService by lazy { RetrofitFactory.create(ApiService::class.java,) }
}