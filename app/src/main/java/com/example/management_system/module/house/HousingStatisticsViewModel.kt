package com.example.management_system.module.house

import com.example.management_system.data.HouseType
import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class HousingStatisticsViewModel: BaseViewModel<HouseRepository>() {
    val houseTypeListData = CommonLiveData<List<HouseType>>()

    fun getAllHouseType(){
        quickLaunch<List<HouseType>> {
            onStart { showLoading() }
            request { repository.getAllHouseType() }
            onSuccess {
                hideLoading()
                houseTypeListData.value = it
            }
            onFailure {
                hideLoading()
                houseTypeListData.failureMessage = it
            }
            onException {
                hideLoading()
                houseTypeListData.exception = ExceptionUtil.parseException2(it)
            }
        }
    }
}