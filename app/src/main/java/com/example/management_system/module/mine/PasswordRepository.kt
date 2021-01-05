package com.example.management_system.module.mine

import com.example.management_system.http.api.ServiceFactory
import com.example.management_system.http.data.BaseResponse

class PasswordRepository {
    suspend fun resetPassword(userName: String, password: String): BaseResponse<String> {
        return ServiceFactory.apiService.updateAdmin(userName, password)
    }
}