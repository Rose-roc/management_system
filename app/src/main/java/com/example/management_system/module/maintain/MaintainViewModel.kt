package com.example.management_system.module.maintain

import com.example.management_system.data.Maintain
import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class MaintainViewModel: BaseViewModel<MaintainRepository>() {
    var maintainListData = CommonLiveData<List<Maintain>>()
    var maintainDeleteData = CommonLiveData<String>()

    fun getAllMaintainList(){
        quickLaunch<List<Maintain>> {
            onStart { showLoading() }
            request { repository.findMaintainList() }
            onSuccess {
                hideLoading()
                maintainListData.value = it
            }
            onException {
                hideLoading()
                maintainListData.exception = ExceptionUtil.parseException2(it)
            }
            onFailure {
                hideLoading()
                maintainListData.failureMessage = it
            }
        }
    }
    fun deleteMaintainById(maintainId:Int){
        quickLaunch<String> {
            onStart { showLoading() }
            request { repository.deleteMaintainById(maintainId) }
            onSuccess {
                hideLoading()
                maintainDeleteData.value = it
            }
            onException {
                hideLoading()
                maintainDeleteData.exception = ExceptionUtil.parseException2(it)
            }
            onFailure {
                hideLoading()
                maintainDeleteData.failureMessage = it
            }
        }
    }
}