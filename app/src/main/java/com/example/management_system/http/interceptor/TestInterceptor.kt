package com.example.management_system.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

object TestInterceptor : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}