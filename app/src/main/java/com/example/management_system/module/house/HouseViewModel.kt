package com.example.management_system.module.house

import androidx.lifecycle.MutableLiveData
import com.example.management_system.base.GeneralRequestState
import com.example.management_system.data.House
import com.example.management_system.data.HouseList
import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class HouseViewModel:BaseViewModel<HouseRepository>() {
    companion object {
        const val DEFAULT_PAGE = 1
        const val PAGE_SIZE = 10
    }

    //当前页
    var mCurrentPage = DEFAULT_PAGE

    private var mIsLoaderMode = false

    var houseListData = CommonLiveData<HouseList>()

    private var mHouseList = mutableListOf<House>()

    val loadState = MutableLiveData<GeneralRequestState>()

    fun onRefresh() {
        mIsLoaderMode = false
        mCurrentPage = DEFAULT_PAGE
        getAllHouse(mCurrentPage, PAGE_SIZE)
    }

    fun onLoadMore() {
        mIsLoaderMode = true
        getAllHouse(++mCurrentPage, PAGE_SIZE)
    }

    private fun getAllHouse(pageNo: Int, pageSize: Int) {
        quickLaunch<HouseList> {
            onStart { showLoading() }
            request {
                repository.getAllHouse(pageNo, pageSize)
            }
            onSuccess {
                // 旧数据
                val oldValue = houseListData.value
                mHouseList.clear()
                if (oldValue != null) {
                    mHouseList.addAll(oldValue.list)
                }
                if (it != null) {
                    if (it.list.isNotEmpty()) {
                        // 新数据非空
                        if (oldValue == null || !mIsLoaderMode) {
                            //这种情况是第一次加载（oldValue == null）或者刷新 （!isLoaderMode）
                            houseListData.value = it
                            loadState.value = GeneralRequestState.SUCCESS
                        } else {
                            // 这种情况是加载更多
                            mHouseList.addAll(it.list)
                            oldValue.list = mHouseList
                            houseListData.value = oldValue
                            loadState.value = GeneralRequestState.LOADER_MORE_SUCCESS
                        }
                    } else {
                        // 新数据为空
                        if (oldValue == null) {
                            // 首次加载为空
                            loadState.value = GeneralRequestState.EMPTY
                        } else {
                            // 加载更多数据为空
                            mCurrentPage--
                            loadState.value = GeneralRequestState.LOADER_MORE_EMPTY
                        }
                    }
                    hideLoading()
                    houseListData.value = it
                }
                onFailure {
                    hideLoading()
                    houseListData.failureMessage = it
                }
                onException {
                    hideLoading()
                    houseListData.exception = ExceptionUtil.parseException2(it)
                }
            }
        }
    }
}