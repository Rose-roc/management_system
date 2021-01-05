package com.example.management_system.module.pay

import com.example.management_system.data.Pay
import com.example.management_system.data.PaymentItem
import com.example.management_system.http.api.ServiceFactory
import com.example.management_system.http.data.BaseResponse

class PayRepository {
    suspend fun findPayList(): BaseResponse<List<Pay>> {
        return ServiceFactory.apiService.getPayList()
    }

    suspend fun deletePayById(payId: Int): BaseResponse<String> {
        return ServiceFactory.apiService.deletePayById(payId)
    }

    suspend fun findPaymentItemList(): BaseResponse<List<PaymentItem>> {
        return ServiceFactory.apiService.findPaymentItemList()
    }

    suspend fun deletePaymentItemByName(paymentItem: String): BaseResponse<String> {
        return ServiceFactory.apiService.deletePaymentItemByName(paymentItem)
    }

    suspend fun insertPaymentItem(name: String, price: Float, unit: String): BaseResponse<String> {
        return ServiceFactory.apiService.insertPaymentItem(name, price, unit)
    }

    suspend fun updatePaymentItem(name: String, price: Float, unit: String): BaseResponse<String> {
        return ServiceFactory.apiService.updatePaymentItem(name, price, unit)
    }
}