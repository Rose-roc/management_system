package com.example.management_system.module.owner

import androidx.lifecycle.MutableLiveData
import com.example.management_system.base.GeneralRequestState
import com.example.management_system.data.Owner
import com.example.management_system.data.OwnerList
import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class SearchResultViewModel : BaseViewModel<OwnerRepository>() {
    companion object {
        const val DEFAULT_PAGE = 1
        const val PAGE_SIZE = 10
    }

    //当前页
    var mCurrentPage = DEFAULT_PAGE

    private var mIsLoaderMode = false

    var ownerListData = CommonLiveData<OwnerList>()

    private var mOwnerList = mutableListOf<Owner>()

    val loadState = MutableLiveData<GeneralRequestState>()

    fun onRefresh(name: String) {
        mIsLoaderMode = false
        mCurrentPage = DEFAULT_PAGE
        getOwners(mCurrentPage, PAGE_SIZE, name)
    }

    fun onLoadMore(name: String) {
        mIsLoaderMode = true
        getOwners(++mCurrentPage, PAGE_SIZE, name)
    }

    private fun getOwners(pageNo: Int, pageSize: Int, name: String) {
        quickLaunch<OwnerList> {
            onStart { showLoading() }
            request {
                repository.getOwnersByName(pageNo, pageSize, name)
            }
            onSuccess {
                // 旧数据
                val oldValue = ownerListData.value
                mOwnerList.clear()
                if (oldValue != null) {
                    mOwnerList.addAll(oldValue.list)
                }
                if (it != null) {
                    if (it.list.isNotEmpty()) {
                        // 新数据非空
                        if (oldValue == null || !mIsLoaderMode) {
                            //这种情况是第一次加载（oldValue == null）或者刷新 （!isLoaderMode）
                            ownerListData.value = it
                            loadState.value = GeneralRequestState.SUCCESS
                        } else {
                            // 这种情况是加载更多
                            mOwnerList.addAll(it.list)
                            oldValue.list = mOwnerList
                            ownerListData.value = oldValue
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
                    ownerListData.value = it
                }
                onFailure {
                    hideLoading()
                    ownerListData.failureMessage = it
                }
                onException {
                    hideLoading()
                    ownerListData.exception = ExceptionUtil.parseException2(it)
                }
            }
        }
    }
}