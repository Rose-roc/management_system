package com.example.management_system.module.account

import com.example.management_system.http.api.ServiceFactory
import com.example.management_system.http.data.BaseResponse

class LoginOrRegisterRepository {
    suspend fun login(name:String, psd:String): BaseResponse<String> {
        return ServiceFactory.apiService.login(name, psd)
    }

    suspend fun register(name:String, psd:String): BaseResponse<String> {
        return ServiceFactory.apiService.register(name, psd)
    }
}