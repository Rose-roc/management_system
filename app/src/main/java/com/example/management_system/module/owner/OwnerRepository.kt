package com.example.management_system.module.owner

import com.example.management_system.data.Family
import com.example.management_system.data.House
import com.example.management_system.data.Owner
import com.example.management_system.data.OwnerList
import com.example.management_system.http.api.ServiceFactory
import com.example.management_system.http.data.BaseResponse

class OwnerRepository {
    suspend fun getOwners(pageNo: Int, pageSize: Int): BaseResponse<OwnerList> {
        return ServiceFactory.apiService.getOwners(pageNo, pageSize)
    }

    suspend fun findHouseById(houseId: Int): BaseResponse<House> {
        return ServiceFactory.apiService.getHouseById(houseId)
    }

    suspend fun findFamilyById(familyId: Int): BaseResponse<Family> {
        return ServiceFactory.apiService.findFamilyById(familyId)
    }

    suspend fun deleteOwner(ownerId: Int): BaseResponse<String> {
        return ServiceFactory.apiService.deleteOwner(ownerId)
    }

    suspend fun updateOwner(ownerId: Int, name: String, phone: String): BaseResponse<String> {
        return ServiceFactory.apiService.updateOwner(ownerId, name, phone)
    }

    suspend fun updateOwner(
        ownerId: Int,
        name: String,
        phone: String,
        houseId: Int,
        familyId: Int
    ): BaseResponse<String> {
        return ServiceFactory.apiService.updateOwner(ownerId, name, phone, houseId, familyId)
    }

    suspend fun getOwnersByName(pageNo: Int, pageSize: Int, name: String): BaseResponse<OwnerList> {
        return ServiceFactory.apiService.getOwnersByName(pageNo, pageSize, name)
    }
}