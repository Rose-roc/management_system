package com.example.management_system.module.maintain

import com.example.management_system.data.Maintain
import com.example.management_system.http.api.ServiceFactory
import com.example.management_system.http.data.BaseResponse

class MaintainRepository {
    suspend fun findMaintainList(): BaseResponse<List<Maintain>> {
        return ServiceFactory.apiService.getMaintainList()
    }

    suspend fun deleteMaintainById(maintainId: Int): BaseResponse<String> {
        return ServiceFactory.apiService.deleteMaintainById(maintainId)
    }
}