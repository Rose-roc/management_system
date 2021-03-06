package com.example.management_system.http.interceptor

import com.example.management_system.http.api.API
import com.zhan.ktwing.delegate.Preference
import com.zhan.ktwing.ext.logd
import okhttp3.Interceptor

object CookieInterceptor {

    // 解析 cookie, 并存储
    var cookie by Preference("cookie", "")

    // cookie 拦截器 获取 cookie (自动登录时候需要用到)
    fun create(): Interceptor {
        logd("cookie interceptor")
        return Interceptor { chain ->
            // 获取 请求
            val request = chain.request()
            val requestUrl = request.url().toString()

            // 如果 是(登录请求 或者 注册请求) 并且 请求头包含 cookie
            if ((requestUrl.contains(API.LOGIN) || requestUrl.contains(API.REGISTER))) {
                val response = chain.proceed(request)
                // 获取 全部 cookie
                val cookies = response.headers("set-cookie")

                if (cookies.size>1){
                    cookie = cookies[1]
                }


                return@Interceptor response
            } else {
                val builder = request.newBuilder()
                if (cookie.isNotEmpty()) {
                    builder.addHeader("Cookie", cookie)
                }

                return@Interceptor chain.proceed(builder.build())
            }
        }
    }
}