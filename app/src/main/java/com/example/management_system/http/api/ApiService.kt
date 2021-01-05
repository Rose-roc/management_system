package com.example.management_system.http.api

import com.example.management_system.data.*
import com.example.management_system.http.data.BaseResponse
import com.zhan.mvvm.annotation.BaseUrl
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@BaseUrl(API.BASE_URL)
interface ApiService {

    @POST(API.LOGIN)
    suspend fun login(
        @Query("userName") userName: String,
        @Query("psd") psd: String
    ): BaseResponse<String>

    @POST(API.REGISTER)
    suspend fun register(
        @Query("userName") userName: String,
        @Query("psd") psd: String
    ): BaseResponse<String>

    @POST(API.UPDATE_ADMIN)
    suspend fun updateAdmin(
        @Query("userName") userName: String,
        @Query("psd") psd: String
    ): BaseResponse<String>

    @GET(API.GET_OWNERS)
    suspend fun getOwners(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): BaseResponse<OwnerList>

    @POST(API.ADD_OWNER)
    suspend fun addOwner(
        @Query("name") name: String,
        @Query("phone") phone: String
    ): BaseResponse<String>

    @DELETE(API.DELETE_OWNER)
    suspend fun deleteOwner(
        @Query("ownerId") ownerId: Int
    ): BaseResponse<String>

    @GET(API.GET_OWNERS_BY_NAME)
    suspend fun getOwnersByName(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int,
        @Query("name") name: String
    ): BaseResponse<OwnerList>

    @POST(API.UPDATE_OWNER)
    suspend fun updateOwner(
        @Query("ownerId") ownerId: Int,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("houseId") houseId: Int,
        @Query("familyId") familyId: Int
    ): BaseResponse<String>

    @POST(API.UPDATE_OWNER)
    suspend fun updateOwner(
        @Query("ownerId") ownerId: Int,
        @Query("name") name: String,
        @Query("phone") phone: String,
    ): BaseResponse<String>


    @GET(API.GET_HOUSES)
    suspend fun getHouses(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): BaseResponse<HouseList>

    @GET(API.GET_HOUSE_BY_ID)
    suspend fun getHouseById(
        @Query("id") id: Int
    ): BaseResponse<House>

    @POST(API.UPDATE_HOUSE_BY_ID)
    suspend fun updateHouseById(
        @Query("houseId") houseId: Int,
        @Query("room") room: Int,
        @Query("building") building: Int,
        @Query("floor") floor: Int,
        @Query("area") area: String,
        @Query("houseType") houseType: String,
        @Query("message") message: String
    ): BaseResponse<String>

    @GET(API.FIND_HOUSE_TYPE_LIST)
    suspend fun getHouseTypeList(): BaseResponse<List<HouseType>>

    @GET(API.FIND_FAMILY_BY_ID)
    suspend fun findFamilyById(
        @Query("familyId") familyId: Int
    ): BaseResponse<Family>

    @GET(API.GET_MAINTAIN_LIST)
    suspend fun getMaintainList(
    ): BaseResponse<List<Maintain>>

    @DELETE(API.DELETE_MAINTAIN_BY_ID)
    suspend fun deleteMaintainById(
        @Query("maintainId") maintainId: Int
    ): BaseResponse<String>

    @GET(API.GET_PAY_LIST)
    suspend fun getPayList(
    ): BaseResponse<List<Pay>>

    @DELETE(API.DELETE_PAY_BY_ID)
    suspend fun deletePayById(
        @Query("payId") payId: Int
    ): BaseResponse<String>

    @GET(API.FIND_PAYMENT_ITEM_LIST)
    suspend fun findPaymentItemList(
    ): BaseResponse<List<PaymentItem>>

    @DELETE(API.DELETE_PAYMENT_ITEM_BY_NAME)
    suspend fun deletePaymentItemByName(
        @Query("paymentItem") paymentItem: String
    ): BaseResponse<String>

    @POST(API.UPDATE_PAYMENT_ITEM)
    suspend fun updatePaymentItem(
        @Query("paymentItem") paymentItem: String,
        @Query("price") price: Float,
        @Query("unit") unit: String
    ): BaseResponse<String>

    @POST(API.INSERT_PAYMENT_ITEM)
    suspend fun insertPaymentItem(
        @Query("paymentItem") paymentItem: String,
        @Query("price") price: Float,
        @Query("unit") unit: String
    ): BaseResponse<String>
}