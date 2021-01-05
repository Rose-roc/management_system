package com.example.management_system.module.mine

import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class PasswordViewModel: BaseViewModel<PasswordRepository>() {
    val userPasswordData = CommonLiveData<String>()

    fun resetPassword(userName: String, password: String){
        quickLaunch<String> {
            onStart { showLoading() }
            request { repository.resetPassword(userName,password) }
            onSuccess {
                hideLoading()
                userPasswordData.value = it
            }
            onFailure {
                hideLoading()
                userPasswordData.failureMessage = it
            }
            onException {
                hideLoading()
                userPasswordData.exception = ExceptionUtil.parseException2(it)
            }
        }
    }
}