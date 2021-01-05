package com.example.management_system.module.pay

import com.example.management_system.data.PaymentItem
import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class PaymentItemViewModel : BaseViewModel<PayRepository>() {
    var payItemListData = CommonLiveData<List<PaymentItem>>()
    var payItemDeleteData = CommonLiveData<String>()
    var payItemAddData = CommonLiveData<String>()
    var payItemUpdateData = CommonLiveData<String>()

    fun findPaymentItemList() {
        quickLaunch<List<PaymentItem>> {
            onStart { showLoading() }
            request { repository.findPaymentItemList() }
            onSuccess {
                payItemListData.value = it
                hideLoading()
            }
            onException {
                hideLoading()
                payItemListData.exception = ExceptionUtil.parseException2(it)
            }
            onFailure {
                hideLoading()
                payItemListData.failureMessage = it
            }
        }
    }

    fun deletePaymentItemByName(name: String) {
        quickLaunch<String> {
            onStart { showLoading() }
            request { repository.deletePaymentItemByName(name) }
            onSuccess {
                payItemDeleteData.value = it
                hideLoading()
            }
            onException {
                hideLoading()
                payItemDeleteData.exception = ExceptionUtil.parseException2(it)
            }
            onFailure {
                hideLoading()
                payItemDeleteData.failureMessage = it
            }
        }
    }

    fun insertPaymentItem(name: String, price: String, unit: String) {
        quickLaunch<String> {
            onStart { showLoading() }
            request { repository.insertPaymentItem(name, price.toFloat(), unit) }
            onSuccess {
                payItemAddData.value = it
                hideLoading()
            }
            onException {
                hideLoading()
                payItemAddData.exception = ExceptionUtil.parseException2(it)
            }
            onFailure {
                hideLoading()
                payItemAddData.failureMessage = it
            }
        }
    }

    fun updatePaymentItem(name: String, price: String, unit: String) {
        quickLaunch<String> {
            onStart { showLoading() }
            request { repository.updatePaymentItem(name, price.toFloat(), unit) }
            onSuccess {
                payItemUpdateData.value = it
                hideLoading()
            }
            onException {
                hideLoading()
                payItemUpdateData.exception = ExceptionUtil.parseException2(it)
            }
            onFailure {
                hideLoading()
                payItemUpdateData.failureMessage = it
            }
        }
    }
}