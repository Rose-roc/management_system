package com.example.management_system.module.pay

import com.example.management_system.data.Owner
import com.example.management_system.data.Pay
import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class PayViewModel:BaseViewModel<PayRepository>() {
    var payListData = CommonLiveData<List<Pay>>()
    var payDeleteData = CommonLiveData<String>()
    var ownerData = CommonLiveData<Owner>()

    fun getPayList(){
        quickLaunch<List<Pay>> {
            onStart { showLoading() }
            request { repository.findPayList() }
            onSuccess {
                hideLoading()
                payListData.value = it
            }
            onException {
                hideLoading()
                payListData.exception = ExceptionUtil.parseException2(it)
            }
            onFailure {
                hideLoading()
                payListData.failureMessage = it
            }
        }
    }

    fun deletePayById(payId:Int){
        quickLaunch<String> {
            onStart { showLoading() }
            request { repository.deletePayById(payId) }
            onSuccess {
                hideLoading()
                payDeleteData.value = it
            }
            onException {
                hideLoading()
                payDeleteData.exception = ExceptionUtil.parseException2(it)
            }
            onFailure {
                hideLoading()
                payDeleteData.failureMessage = it
            }
        }
    }


}