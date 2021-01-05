package com.example.management_system.module.house

import com.example.management_system.data.House
import com.example.management_system.data.HouseList
import com.example.management_system.data.HouseType
import com.example.management_system.http.api.ServiceFactory
import com.example.management_system.http.data.BaseResponse

class HouseRepository {
    suspend fun getAllHouse(pageNo: Int, pageSize: Int): BaseResponse<HouseList> {
        return ServiceFactory.apiService.getHouses(pageNo, pageSize)
    }

    suspend fun getAllHouseType(): BaseResponse<List<HouseType>>{
        return  ServiceFactory.apiService.getHouseTypeList()
    }
}