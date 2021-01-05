package com.example.management_system.module.account

import android.text.TextUtils
import com.example.management_system.R
import com.example.management_system.utils.ExceptionUtil
import com.zhan.mvvm.mvvm.BaseViewModel
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class LoginOrRegisterViewModel : BaseViewModel<LoginOrRegisterRepository>() {
    val loginData = CommonLiveData<String>()
    val registerData = CommonLiveData<String>()

    fun login(name: String, psw: String) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psw)) {
            showToast(R.string.account_or_password_empty)
            return
        }

        quickLaunch<String> {
            onStart {
                showLoading()
            }

            request {
                repository.login(name, psw)
            }

            onSuccess {
                hideLoading()
                loginData.value = it
            }

            onFailure {
                hideLoading()
                loginData.failureMessage = it

            }

            onException {
                hideLoading()
                loginData.exception = ExceptionUtil.parseException2(it)
            }
        }

    }

    fun register(name: String, psw: String) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psw)) {
            showToast(R.string.account_or_password_empty)
            return
        }

        quickLaunch<String> {
            onStart {
                showLoading()
            }

            request {
                repository.register(name, psw)
            }

            onSuccess {
                hideLoading()
                loginData.value = it
            }

            onFailure {
                hideLoading()
                loginData.failureMessage = it

            }

            onException {
                hideLoading()
                loginData.exception = ExceptionUtil.parseException2(it)
            }
        }
    }
}