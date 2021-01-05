package com.example.management_system.http

import com.example.management_system.base.gson.BaseConverterFactory
import com.zhan.mvvm.http.BaseOkHttpClient
import com.zhan.mvvm.http.BaseRetrofitConfig
import com.example.management_system.http.interceptor.CookieInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class MyRetrofitConfig : BaseRetrofitConfig() {

    override fun initOkHttpClient(): OkHttpClient {
        return BaseOkHttpClient.create(CookieInterceptor.create())
    }

    override fun initRetrofit(baseUrl: String): Retrofit {

        //关联okHttp并加上rxJava和 Gson 的配置和baseUrl
        return Retrofit.Builder()
            .client(initOkHttpClient())
                //配置 gson 拦截器，自动跳转登陆界面
            .addConverterFactory(BaseConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }
}